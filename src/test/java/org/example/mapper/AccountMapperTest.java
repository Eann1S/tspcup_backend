package org.example.mapper;

import org.example.dto.RegisterDto;
import org.example.entity.Account;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.InstancioSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({MockitoExtension.class, InstancioExtension.class})
class AccountMapperTest {

    private AccountMapper accountMapper;

    @BeforeEach
    void setUp() {
        accountMapper = new AccountMapperImpl();
    }

    @ParameterizedTest
    @InstancioSource
    void shouldMapRegisterDtoToAccount(RegisterDto registerDto) {
        Account account = accountMapper.mapRegisterDtoToAccount(registerDto);

        assertThat(account)
                .extracting(
                        Account::getFirstName,
                        Account::getLastName,
                        Account::getGroup,
                        Account::getNameTeam,
                        Account::getTelegram,
                        Account::getEmail
                )
                .containsExactly(
                        registerDto.firstName(),
                        registerDto.lastName(),
                        registerDto.group(),
                        registerDto.nameTeam(),
                        registerDto.telegram(),
                        registerDto.email()
                );
    }
}