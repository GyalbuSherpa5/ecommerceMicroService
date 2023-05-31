package com.don.apigatewayservice.exception;

import com.don.apigatewayservice.dto.ApiResponse;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MustLoginException.class)
    public Mono<ServerResponse> handleMustLoginException(ServerWebExchange exchange, MustLoginException exception) {
        exchange.getAttributes().putIfAbsent(ErrorAttributes.ERROR_ATTRIBUTE, exception);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError("PLEASE_LOG_IN");
        apiResponse.setMessage(exception.getReason());

        return ServerResponse.status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(apiResponse));
    }

    @ExceptionHandler(RoleNotMatchedException.class)
    public Mono<ServerResponse> roleNotMatch(ServerWebExchange exchange, RoleNotMatchedException exception) {
        exchange.getAttributes().putIfAbsent(ErrorAttributes.ERROR_ATTRIBUTE, exception);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError("ROLE_MUST_BE_ADMIN");
        apiResponse.setMessage(exception.getReason());

        return ServerResponse.status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(apiResponse));
    }


}
