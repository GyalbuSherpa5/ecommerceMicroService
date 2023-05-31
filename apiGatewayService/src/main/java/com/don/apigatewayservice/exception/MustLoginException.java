package com.don.apigatewayservice.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class MustLoginException extends ResponseStatusException {
    public MustLoginException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
