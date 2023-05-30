package com.don.userservice.exception;

import com.don.userservice.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ApiResponse> userAlreadyExist(UserAlreadyExistException exception){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError("USER_ALREADY_EXIST");
        apiResponse.setMessage(exception.getMessage());

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
