package com.don.apigatewayservice.exception;

import com.don.apigatewayservice.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MustLoginException.class)
    public ResponseEntity<ApiResponse> notValidUser(MustLoginException exception){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError("PLEASE_LOG_IN");
        apiResponse.setMessage(exception.getMessage());

        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
    }

}
