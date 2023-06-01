package com.don.userservice.exception;

public class UserDoNotExistException extends RuntimeException{
    public UserDoNotExistException(String message) {
        super(message);
    }
}
