package com.don.userservice.exception;

import com.don.userservice.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ApiResponse> userAlreadyExist(UserAlreadyExistException exception){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError("USER_ALREADY_EXIST");
        apiResponse.setMessage(exception.getMessage());

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserDoNotExistException.class)
    public ResponseEntity<ApiResponse> userDoNotExist(UserDoNotExistException exception){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError("USER_D0_NOT_EXIST");
        apiResponse.setMessage(exception.getMessage());

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errorMap);
    }

}
