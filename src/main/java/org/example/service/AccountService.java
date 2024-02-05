package org.example.service;

import org.example.dto.RegisterDto;
import org.example.entity.Account;

public interface AccountService {
    Account createAccountFrom(RegisterDto registerDto);

    int getTeamSize(String nameTeam);
}
