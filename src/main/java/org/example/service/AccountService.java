package org.example.service;

import org.example.dto.RegisterDto;

public interface AccountService {
    void createAccountFrom(RegisterDto registerDto);
}
