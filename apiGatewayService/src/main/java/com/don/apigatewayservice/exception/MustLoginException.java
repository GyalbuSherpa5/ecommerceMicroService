package com.don.apigatewayservice.exception;

public class MustLoginException extends RuntimeException{
    public MustLoginException(String message) {
        super(message);
    }
}
