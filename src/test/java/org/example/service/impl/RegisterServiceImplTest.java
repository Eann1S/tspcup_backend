package org.example.service.impl;

import org.example.dto.RegisterRequest;
import org.example.entity.Account;
import org.example.exception.MoreTeamMembersNotAllowed;
import org.example.service.AccountService;
import org.example.service.strategy.sending_message_strategy.SendingMessageStrategy;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.InstancioSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.message.ErrorMessage.MORE_TEAM_MEMBERS_NOT_ALLOWED;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({InstancioExtension.class, MockitoExtension.class})
class RegisterServiceImplTest {

    @Mock
    private AccountService accountService;
    @Mock
    private SendingMessageStrategy sendingMessageStrategy;
    private RegisterServiceImpl registerService;

    @BeforeEach
    void setUp() {
        registerService = new RegisterServiceImpl(accountService, sendingMessageStrategy);
    }

    @ParameterizedTest
    @InstancioSource
    void shouldRegister(RegisterRequest registerRequest, Account account) {
        when(accountService.createAccountFrom(registerRequest))
                .thenReturn(account);

        registerService.register(registerRequest);

        verify(sendingMessageStrategy).sendMessageToAccount(account);
    }

    @ParameterizedTest
    @InstancioSource
    void shouldThrowExceptionIfMoreMembersInTeamNotAllowed(RegisterRequest registerRequest) {
        when(accountService.getTeamSize(registerRequest.nameTeam()))
                .thenReturn(5);

        assertThatThrownBy(() -> registerService.register(registerRequest))
                .isInstanceOf(MoreTeamMembersNotAllowed.class)
                .hasMessage(MORE_TEAM_MEMBERS_NOT_ALLOWED.getMessage());
    }
}