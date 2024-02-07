package org.example.service;

import org.example.dto.RegisterRequest;

public interface RegisterService {

    int MAX_TEAM_CAPACITY = 5;

    void register(RegisterRequest registerRequest);
}
