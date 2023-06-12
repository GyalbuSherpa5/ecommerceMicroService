package com.don.userservice.service;

import com.don.userservice.model.UserCredential;

public interface UserCredentialService {
    void saveUser(UserCredential userCredential);
    Long getUserId(String userName);
    void updateUser(UserCredential userCredential, Long userId);
    void deleteUser(Long userId);
}
