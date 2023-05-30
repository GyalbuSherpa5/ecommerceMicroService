package com.don.productservice.exception;

public class ProductCategoryDoNotExistException extends RuntimeException{
    public ProductCategoryDoNotExistException(String message) {
        super(message);
    }
}
