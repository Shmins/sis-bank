package com.bank.app.entity.client.exception;

public class GenericException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public GenericException(String message) {
        super(message);
    }
}
