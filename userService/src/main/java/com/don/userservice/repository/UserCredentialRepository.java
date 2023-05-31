package com.don.userservice.repository;

import com.don.userservice.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {
    Optional<UserCredential> findByUserName(String userName);

    @Query(value = "select role from user_credential where user_name=?1",nativeQuery = true)
    String getUserRole(String userName);
}
