package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.RegisterDto;
import org.example.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.example.message.InfoMessage.REGISTER_SUCCESS;

@RestController("/api/v1")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        registerService.register(registerDto);
        return ResponseEntity.ok(
                REGISTER_SUCCESS.getMessage()
        );
    }
}
