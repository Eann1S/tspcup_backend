package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.RegisterDto;
import org.example.entity.Account;
import org.example.mapper.AccountMapper;
import org.example.repository.AccountRepository;
import org.example.service.AccountService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public void createAccountFrom(RegisterDto registerDto) {
        Account account = accountMapper.mapRegisterDtoToAccount(registerDto);
        accountRepository.saveAndFlush(account);
    }
}
