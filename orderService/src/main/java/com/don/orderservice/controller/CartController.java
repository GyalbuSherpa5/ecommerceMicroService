package com.don.orderservice.controller;

import com.don.orderservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final JwtUtil jwtUtil;

    @GetMapping("/don")
    public ResponseEntity<String> example(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String authorizationKey = authorizationHeader.substring(7); // Exclude "Bearer " prefix
            // Do something with the authorization key

            String username = jwtUtil.extractUserName(authorizationKey); // Extract the username

            return ResponseEntity.ok("Username: " + username);

        } else {
            // Handle missing or invalid authorization header
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid authorization header");
        }
    }
}

