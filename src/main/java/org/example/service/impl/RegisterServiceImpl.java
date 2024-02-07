package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RegisterRequest;
import org.example.entity.Account;
import org.example.exception.AccountAlreadyExistsException;
import org.example.exception.TeamCapacityExceededException;
import org.example.service.AccountService;
import org.example.service.RegisterService;
import org.example.service.strategy.sending_message_strategy.SendingMessageStrategy;

@RequiredArgsConstructor
@Slf4j
public class RegisterServiceImpl implements RegisterService {

    private final AccountService accountService;
    private final SendingMessageStrategy sendingMessageStrategy;

    @Override
    public void register(RegisterRequest registerRequest) {
        validateRegisterRequest(registerRequest);
        Account account = accountService.createAccountFrom(registerRequest);
        sendingMessageStrategy.sendMessageToAccount(account);
        log.info("{} was registered", registerRequest.email());
    }

    private void validateRegisterRequest(RegisterRequest registerRequest) {
        ensureTeamCapacityIsNotExceeded(registerRequest.nameTeam());
        ensureAccountDoesNotExist(registerRequest);
    }

    private void ensureTeamCapacityIsNotExceeded(String nameTeam) {
        int teamSize = accountService.getTeamSize(nameTeam);
        if (teamSize >= MAX_TEAM_CAPACITY) {
            throw new TeamCapacityExceededException(nameTeam, MAX_TEAM_CAPACITY);
        }
    }

    private void ensureAccountDoesNotExist(RegisterRequest registerRequest) {
        throwExceptionIfAccountExists(
                accountService.accountExistsWithFirstNameAndLastName(registerRequest.firstName(), registerRequest.lastName()),
                "%s %s".formatted(registerRequest.firstName(), registerRequest.lastName())
        );
        throwExceptionIfAccountExists(
                accountService.accountExistsWithTelegram(registerRequest.telegram()),
                registerRequest.telegram()
        );
        throwExceptionIfAccountExists(
                accountService.accountExistsWithEmail(registerRequest.email()),
                registerRequest.email()
        );
    }

    private void throwExceptionIfAccountExists(boolean exists, String accountDetail) {
        if (exists) {
            throw new AccountAlreadyExistsException(accountDetail);
        }
    }
}
