package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RegisterRequest;
import org.example.entity.Account;
import org.example.exception.MoreTeamMembersNotAllowed;
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
        throwExceptionIfThereAreAlreadyFiveMembersInTeam(registerRequest.nameTeam());
        Account account = accountService.createAccountFrom(registerRequest);
        sendingMessageStrategy.sendMessageToAccount(account);
        log.info("{} was registered", registerRequest.email());
    }

    private void throwExceptionIfThereAreAlreadyFiveMembersInTeam(String nameTeam) {
        int teamSize = accountService.getTeamSize(nameTeam);
        if (teamSize >= 5) {
            throw new MoreTeamMembersNotAllowed();
        }
    }
}
