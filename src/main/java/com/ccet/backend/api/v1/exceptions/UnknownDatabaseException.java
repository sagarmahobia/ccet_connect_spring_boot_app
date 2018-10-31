package com.ccet.backend.api.v1.exceptions;

public class UnknownDatabaseException extends RuntimeException {

    private static final int ERROR_CODE = 1004;

    public UnknownDatabaseException() {
        super("UnknownDatabaseException");
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
