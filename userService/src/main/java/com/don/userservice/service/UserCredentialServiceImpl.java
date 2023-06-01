package com.don.userservice.service;

import com.don.userservice.exception.UserAlreadyExistException;
import com.don.userservice.exception.UserDoNotExistException;
import com.don.userservice.model.UserCredential;
import com.don.userservice.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCredentialServiceImpl implements UserCredentialService {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void saveUser(UserCredential userCredential) {

        if (userCredentialRepository.findByUserName(userCredential.getUserName()).isPresent()) {
            throw new UserAlreadyExistException("This user already exist");
        }

        userCredential.setPassword(
                passwordEncoder.encode(userCredential.getPassword())
        );
        userCredential.setRole("ROLE_USER");
        userCredentialRepository.save(userCredential);
    }

    @Override
    public Long getUserId(String userName) {
        return userCredentialRepository.getUserId(userName)
                .orElseThrow(() -> new UserDoNotExistException(
                        "User with name " + userName + " do not exist")
                );
    }

    public String generateToken(String userName) {
        String role = userCredentialRepository.getUserRole(userName)
                .orElseThrow(() -> new UserDoNotExistException(
                        "User with name " + userName + " do not exist")
                );
        return jwtService.generateToken(userName, role);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}
