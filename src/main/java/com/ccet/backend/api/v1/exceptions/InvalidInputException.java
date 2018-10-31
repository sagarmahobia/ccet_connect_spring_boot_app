package com.ccet.backend.api.v1.exceptions;

public class InvalidInputException extends RuntimeException {

    private static final int ERROR_CODE = 1002;

    public InvalidInputException() {
        super("InvalidInputException");
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
