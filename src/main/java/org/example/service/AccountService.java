package org.example.service;

import org.example.dto.RegisterRequest;
import org.example.entity.Account;

public interface AccountService {
    Account createAccountFrom(RegisterRequest registerRequest);

    int getTeamSize(String nameTeam);
}
