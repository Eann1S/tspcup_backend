package org.example.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    MORE_TEAM_MEMBERS_NOT_ALLOWED("В команде уже находится 5 человек.");

    private final String message;
}
