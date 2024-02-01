package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.RegisterDto;
import org.example.service.AccountService;
import org.example.service.RegisterService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final AccountService accountService;

    @Override
    public void register(RegisterDto registerDto) {
        accountService.createAccountFrom(registerDto);
    }
}
