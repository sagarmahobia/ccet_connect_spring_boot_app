package com.ccet.backend.api.v1.exceptions;

public class InternalServerException extends RuntimeException {

    private static final int ERROR_CODE = 1006;

    public InternalServerException() {
        super("InternalServerException");
    }

    public InternalServerException(String msg) {
        super(msg);
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
