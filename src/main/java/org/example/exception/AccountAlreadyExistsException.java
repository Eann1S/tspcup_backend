package org.example.exception;

import static org.example.message.ErrorMessage.ACCOUNT_ALREADY_EXISTS;

public class AccountAlreadyExistsException extends RuntimeException {

    public AccountAlreadyExistsException(Object accountDetail) {
        super(ACCOUNT_ALREADY_EXISTS.formatWith(accountDetail));
    }
}
