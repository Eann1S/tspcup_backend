package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.InfoDto;
import org.example.dto.RegisterRequest;
import org.example.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.example.message.InfoMessage.REGISTER_SUCCESS;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<InfoDto> register(@RequestBody RegisterRequest registerRequest) {
        registerService.register(registerRequest);
        return ResponseEntity.ok(
                new InfoDto(REGISTER_SUCCESS.getMessage())
        );
    }
}
