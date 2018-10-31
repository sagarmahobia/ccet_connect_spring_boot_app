package com.ccet.backend.api.v1.exceptions;

public class InvalidCredentialsException  extends RuntimeException {

    private static final int ERROR_CODE = 1003;

    public InvalidCredentialsException() {
        super("InvalidCredentialsException");
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }
}