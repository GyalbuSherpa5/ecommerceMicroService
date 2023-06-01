package com.don.apigatewayservice.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/users/register",
            "/users/login",
            "/users/getUserId/**",
            "/products/getAllProducts/**",
            "/products/getById/**",
            "/products/getByAttribute/**",
            "/getProductByName/**",
            "/eureka"
    );
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> new AntPathMatcher().match(uri, request.getURI().getPath()));

    public static final List<String> adminEndpoints = List.of(
            "/products/addProduct"
    );
    public Predicate<ServerHttpRequest> isAdminAccess =
            request -> adminEndpoints
                    .stream()
                    .anyMatch(uri -> new AntPathMatcher().match(uri, request.getURI().getPath()));
}

