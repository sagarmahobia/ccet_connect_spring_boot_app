package com.ccet.backend.api.v1.exceptions;

public class UserNotFoundException extends RuntimeException {

    private static final int ERROR_CODE = 1001;

    public UserNotFoundException() {
        super("UserNotFoundException");
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }

}
