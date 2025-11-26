package com.example.exceptions;

public class BaseApiException extends RuntimeException {

    public BaseApiException(String message) {
        super(message);
    }

    public String getExceptionType() {
        return getClass().getSimpleName();
    }
}