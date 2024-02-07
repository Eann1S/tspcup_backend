package test_util;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.repository.AccountRepository;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
@RequiredArgsConstructor
public class IntegrationTestUtil {

    private final AccountRepository accountRepository;

    public void saveAccountToDatabase(Account account) {
        accountRepository.saveAndFlush(account);
    }
}
