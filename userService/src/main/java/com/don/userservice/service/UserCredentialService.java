package com.don.userservice.service;

import com.don.userservice.dto.UserResponse;
import com.don.userservice.model.UserCredential;

import java.util.List;

public interface UserCredentialService {
    void saveUser(UserCredential userCredential);
    Long getUserId(String userName);
    void updateUser(UserCredential userCredential, Long userId);
    void deleteUser(Long userId);
    UserResponse getUserByName(String name);
    List<UserResponse> getAllUser();
}
