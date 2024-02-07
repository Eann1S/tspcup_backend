package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RegisterRequest;
import org.example.entity.Account;
import org.example.mapper.AccountMapper;
import org.example.repository.AccountRepository;
import org.example.service.AccountService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public Account createAccountFrom(RegisterRequest registerRequest) {
        Account account = accountMapper.mapRegisterDtoToAccount(registerRequest);
        accountRepository.saveAndFlush(account);
        log.info("account {} was created", account.getId());
        return account;
    }

    @Override
    public int getTeamSize(String nameTeam) {
        return accountRepository.findAllByNameTeam(nameTeam).size();
    }

    @Override
    public boolean accountExistsWithFirstNameAndLastName(String firstName, String lastName) {
        return accountRepository.existsByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public boolean accountExistsWithTelegram(String telegram) {
        return accountRepository.existsByTelegram(telegram);
    }

    @Override
    public boolean accountExistsWithEmail(String email) {
        return accountRepository.existsByEmail(email);
    }
}
