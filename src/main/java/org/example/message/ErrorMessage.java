package org.example.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    TEAM_CAPACITY_IS_EXCEEDED("В команде %s уже находится %s человек."),
    ACCOUNT_ALREADY_EXISTS("Аккаунт %s уже существует");

    private final String message;

    public String formatWith(Object... params) {
        return message.formatted(params);
    }
}
