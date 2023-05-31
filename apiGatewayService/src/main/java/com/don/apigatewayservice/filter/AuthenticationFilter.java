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

import java.util.Objects;
import java.util.logging.Logger;

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
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    log.error("Sign In required");
                    throw new MustLoginException(HttpStatus.UNAUTHORIZED, "Sign in required");
                }

                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                jwtUtil.validateToken(authHeader);
                String role = jwtUtil.extractUserRole(authHeader);

                // Check if the user has admin role
                if (validator.isAdminAccess.test(exchange.getRequest())
                        && role.equals("ROLE_admin")
                ) {
                    log.info("User with " + role + " accessing the endpoint");
                } else {
                    // User does not have admin role, deny access
                    log.error("User does not have admin role. Unauthorized access to the endpoint");
                    throw new RoleNotMatchedException(HttpStatus.FORBIDDEN, "only admin can access");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}


