package com.don.apigatewayservice.filter;

import com.don.apigatewayservice.exception.MustLoginException;
import com.don.apigatewayservice.exception.RoleNotMatchedException;
import com.don.apigatewayservice.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private final RouteValidator validator;
    private final JwtUtil jwtUtil;
    public AuthenticationFilter(RouteValidator validator, JwtUtil jwtUtil) {
        super(Config.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                HttpHeaders headers = exchange.getRequest().getHeaders();
                if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new MustLoginException(HttpStatus.UNAUTHORIZED, "Sign in required");
                }

                String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                jwtUtil.validateToken(authHeader);
                String role = jwtUtil.extractUserRole(authHeader);

                if (validator.isAdminAccess.test(exchange.getRequest()) && role.equals("ROLE_admin")) {
                    log.info("User with " + role + " accessing the endpoint");
                } else {
                    log.error("User does not have admin role. Unauthorized access to the endpoint");
                    throw new RoleNotMatchedException(HttpStatus.FORBIDDEN, "Only admin can access");
                }
            }
            return chain.filter(exchange);
        };
    }
    public static class Config {

    }
}


