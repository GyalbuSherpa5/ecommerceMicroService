package com.don.userservice.mapper;

import com.don.userservice.dto.UserResponse;
import com.don.userservice.model.UserCredential;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserToResponseMapper implements Function<UserCredential, UserResponse> {
    @Override
    public UserResponse apply(UserCredential userCredential) {
        return new UserResponse(
                userCredential.getUserName(),
                userCredential.getEmail()
        );
    }
}
