package com.ccet.backend.api.v1.exceptions;

public class EmailAlreadyUsedException extends RuntimeException {

    private static final int ERROR_CODE = 1005;

    public EmailAlreadyUsedException() {
        super("EmailAlreadyUsedException");
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
