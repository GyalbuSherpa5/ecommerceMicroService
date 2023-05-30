package com.don.productservice.exception;

public class ProductDoNotExistException extends RuntimeException {
    public ProductDoNotExistException(String message) {
        super(message);
    }
}
