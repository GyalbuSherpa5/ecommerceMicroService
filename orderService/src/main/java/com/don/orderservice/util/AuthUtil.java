package com.don.orderservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final JwtUtil jwtUtil;

    public String getUserName(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Exclude "Bearer " prefix
            String authorizationKey = authorizationHeader.substring(7);
            return jwtUtil.extractUserName(authorizationKey); // Extract the username

        } else {
            return null;
        }
    }
}
