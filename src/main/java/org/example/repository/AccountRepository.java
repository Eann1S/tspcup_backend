package org.example.repository;

import org.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    List<Account> findAllByNameTeam(String nameTeam);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    boolean existsByTelegram(String telegram);

    boolean existsByEmail(String email);
}
