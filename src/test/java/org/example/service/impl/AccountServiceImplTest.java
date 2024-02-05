package org.example.service.impl;

import org.example.dto.RegisterDto;
import org.example.entity.Account;
import org.example.mapper.AccountMapper;
import org.example.repository.AccountRepository;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.InstancioSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({InstancioExtension.class, MockitoExtension.class})
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountMapper accountMapper;
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl(accountRepository, accountMapper);
    }

    @ParameterizedTest
    @InstancioSource
    void shouldCreateAccount(RegisterDto registerDto, Account account) {
        when(accountMapper.mapRegisterDtoToAccount(registerDto))
                .thenReturn(account);

        accountService.createAccountFrom(registerDto);

        verify(accountRepository).saveAndFlush(account);
    }

    @ParameterizedTest
    @InstancioSource
    void shouldReturnTeamSize(String teamName, List<Account> team) {
        when(accountRepository.findAllByNameTeam(teamName))
                .thenReturn(team);

        int teamSize = accountService.getTeamSize(teamName);

        assertThat(teamSize).isEqualTo(team.size());
    }
}