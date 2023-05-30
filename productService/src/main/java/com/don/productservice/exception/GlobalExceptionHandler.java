package com.don.productservice.exception;

import com.don.productservice.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductAlreadyExistException.class)
    public ResponseEntity<ApiResponse> alreadyExistException(ProductAlreadyExistException exception) {

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError("PRODUCT_ALREADY_EXIST");
        apiResponse.setMessage(exception.getMessage());

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductDoNotExistException.class)
    public ResponseEntity<ApiResponse> alreadyExistException(ProductDoNotExistException exception) {

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError("PRODUCT_DO_NOT_EXIST");
        apiResponse.setMessage(exception.getMessage());

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductCategoryDoNotExistException.class)
    public ResponseEntity<ApiResponse> categoryDoNotExistException(ProductCategoryDoNotExistException exception) {

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError("PRODUCT_CATEGORY_DO_NOT_EXIST");
        apiResponse.setMessage(exception.getMessage());

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

}
