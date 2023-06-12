package com.don.userservice.service;

import com.don.userservice.exception.UserAlreadyExistException;
import com.don.userservice.exception.UserDoNotExistException;
import com.don.userservice.model.UserCredential;
import com.don.userservice.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCredentialServiceImpl implements UserCredentialService {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void saveUser(UserCredential userCredential) {

        if (userCredentialRepository.findByUserName(userCredential.getUserName()).isPresent()) {
            log.error("user already exist in database");
            throw new UserAlreadyExistException("This user already exist");
        }

        userCredential.setPassword(
                passwordEncoder.encode(userCredential.getPassword())
        );
        userCredential.setRole("ROLE_USER");

        userCredentialRepository.save(userCredential);
        log.info("user saved successfully");
    }

    @Override
    public Long getUserId(String userName) {
        log.info("Fetching user");
        return userCredentialRepository.getUserId(userName)
                .orElseThrow(() -> {
                            log.error("user does not exist");
                            return new UserDoNotExistException(
                                    "User with name " + userName + " do not exist");
                        }
                );
    }

    public String generateToken(String userName) {
        log.info("generating token");
        String role = userCredentialRepository.getUserRole(userName)
                .orElseThrow(() -> {
                    log.error("user do not exist");
                    return new UserDoNotExistException(
                            "User with name " + userName + " do not exist");
                });
        return jwtService.generateToken(userName, role);
    }

    public void validateToken(String token) {
        log.info("validating token");
        jwtService.validateToken(token);
    }
}
