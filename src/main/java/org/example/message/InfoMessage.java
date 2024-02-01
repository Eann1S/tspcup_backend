package org.example.message;

public enum InfoMessage {

    REGISTER_SUCCESS("Вы успешно зарегестрировались");

    private final String message;

    InfoMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
