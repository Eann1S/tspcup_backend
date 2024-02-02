package org.example.service.impl;

import org.example.dto.RegisterDto;
import org.example.service.AccountService;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.InstancioSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith({InstancioExtension.class, MockitoExtension.class})
class RegisterServiceImplTest {

    @Mock
    private AccountService accountService;
    private RegisterServiceImpl registerService;

    @BeforeEach
    void setUp() {
        registerService = new RegisterServiceImpl(accountService);
    }

    @ParameterizedTest
    @InstancioSource
    void shouldRegister(RegisterDto registerDto) {
        registerService.register(registerDto);

        verify(accountService).createAccountFrom(registerDto);
    }
}