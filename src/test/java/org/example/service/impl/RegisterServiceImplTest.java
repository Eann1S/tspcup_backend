package org.example.service.impl;

import org.example.dto.RegisterRequest;
import org.example.entity.Account;
import org.example.exception.AccountAlreadyExistsException;
import org.example.exception.TeamCapacityExceededException;
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
import static org.example.message.ErrorMessage.ACCOUNT_ALREADY_EXISTS;
import static org.example.message.ErrorMessage.TEAM_CAPACITY_IS_EXCEEDED;
import static org.example.service.impl.RegisterServiceImpl.MAX_TEAM_CAPACITY;
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
    void shouldThrowException_whenTeamCapacityIsExceeded(RegisterRequest registerRequest) {
        when(accountService.getTeamSize(registerRequest.nameTeam()))
                .thenReturn(MAX_TEAM_CAPACITY);

        assertThatThrownBy(() -> registerService.register(registerRequest))
                .isInstanceOf(TeamCapacityExceededException.class)
                .hasMessage(
                        TEAM_CAPACITY_IS_EXCEEDED.formatWith(registerRequest.nameTeam(), MAX_TEAM_CAPACITY)
                );
    }

    @ParameterizedTest
    @InstancioSource
    void shouldThrowException_whenAccountWithGivenFirstNameAndLastNameExists(RegisterRequest registerRequest) {
        when(accountService.accountExistsWithFirstNameAndLastName(registerRequest.firstName(), registerRequest.lastName()))
                .thenReturn(true);

        assertThatThrownBy(() -> registerService.register(registerRequest))
                .isInstanceOf(AccountAlreadyExistsException.class)
                .hasMessage(
                        ACCOUNT_ALREADY_EXISTS.formatWith(
                                "%s %s".formatted(registerRequest.firstName(), registerRequest.lastName())
                        )
                );
    }

    @ParameterizedTest
    @InstancioSource
    void shouldThrowException_whenAccountWithGivenTelegramExists(RegisterRequest registerRequest) {
        when(accountService.accountExistsWithTelegram(registerRequest.telegram()))
                .thenReturn(true);

        assertThatThrownBy(() -> registerService.register(registerRequest))
                .isInstanceOf(AccountAlreadyExistsException.class)
                .hasMessage(
                        ACCOUNT_ALREADY_EXISTS.formatWith(registerRequest.telegram())
                );
    }

    @ParameterizedTest
    @InstancioSource
    void shouldThrowException_whenAccountWithGivenEmailExists(RegisterRequest registerRequest) {
        when(accountService.accountExistsWithEmail(registerRequest.email()))
                .thenReturn(true);

        assertThatThrownBy(() -> registerService.register(registerRequest))
                .isInstanceOf(AccountAlreadyExistsException.class)
                .hasMessage(
                        ACCOUNT_ALREADY_EXISTS.formatWith(registerRequest.email())
                );
    }
}