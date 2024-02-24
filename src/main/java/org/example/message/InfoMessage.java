package org.example.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InfoMessage {

    REGISTER_SUCCESS("Вы успешно зарегестрировались");

    private final String message;
}
