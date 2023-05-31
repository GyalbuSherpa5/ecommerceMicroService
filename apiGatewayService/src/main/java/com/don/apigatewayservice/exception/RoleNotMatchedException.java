package com.don.apigatewayservice.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class RoleNotMatchedException extends ResponseStatusException {
    public RoleNotMatchedException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
