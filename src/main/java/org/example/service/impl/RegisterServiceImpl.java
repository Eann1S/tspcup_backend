package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RegisterDto;
import org.example.service.AccountService;
import org.example.service.RegisterService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterServiceImpl implements RegisterService {

    private final AccountService accountService;

    @Override
    public void register(RegisterDto registerDto) {
        accountService.createAccountFrom(registerDto);
        log.info("{} was registered", registerDto.email());
    }
}
