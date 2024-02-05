package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RegisterDto;
import org.example.entity.Account;
import org.example.exception.MoreTeamMembersNotAllowed;
import org.example.service.AccountService;
import org.example.service.RegisterService;
import org.example.service.strategy.sending_message_strategy.SendingMessageStrategy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterServiceImpl implements RegisterService {

    private final AccountService accountService;
    private final SendingMessageStrategy sendingMessageStrategy;

    @Override
    public void register(RegisterDto registerDto) {
        throwExceptionIfThereAreAlreadyFiveMembersInTeam(registerDto.nameTeam());
        Account account = accountService.createAccountFrom(registerDto);
        sendingMessageStrategy.sendMessageToAccount(account);
        log.info("{} was registered", registerDto.email());
    }

    private void throwExceptionIfThereAreAlreadyFiveMembersInTeam(String nameTeam) {
        int teamSize = accountService.getTeamSize(nameTeam);
        if (teamSize >= 5) {
            throw new MoreTeamMembersNotAllowed();
        }
    }
}
