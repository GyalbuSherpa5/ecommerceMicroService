package com.don.userservice.controller;

import com.don.userservice.dto.JwtRequest;
import com.don.userservice.dto.JwtResponse;
import com.don.userservice.dto.UserLogin;
import com.don.userservice.dto.UserResponse;
import com.don.userservice.model.RefreshToken;
import com.don.userservice.model.UserCredential;
import com.don.userservice.service.RefreshTokenService;
import com.don.userservice.service.UserCredentialService;
import com.don.userservice.service.UserCredentialServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserCredentialController {
    private final UserCredentialService userCredentialService;
    private final UserCredentialServiceImpl userCredentialServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public String saveUser(@RequestBody @Valid UserCredential userCredential) {
        userCredentialService.saveUser(userCredential);
        return "User saved successfully";
    }

    @PutMapping("/update/{userId}")
    public String updateUser(@RequestBody @Valid UserCredential userCredential, @PathVariable Long userId) {
        userCredentialService.updateUser(userCredential, userId);
        return "User updated successfully";
    }

    @PostMapping("/login")
    public JwtResponse getToken(@RequestBody UserLogin userLogin) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLogin.getUserName(),
                        userLogin.getPassword()
                )
        );

        if (authenticate.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userLogin.getUserName());
            return JwtResponse.builder()
                    .accessToken(userCredentialServiceImpl.generateToken(userLogin.getUserName()))
                    .token(refreshToken.getToken())
                    .build();
        } else {
            throw new RuntimeException("Invalid Access");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody JwtRequest jwtRequest){
        return refreshTokenService
                .findByToken(jwtRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserCredential)
                .map(userCredential -> {
                    String accessToken = userCredentialServiceImpl
                            .generateToken(userCredential.getUserName());
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .token(jwtRequest.getToken())
                            .build();
                }).orElseThrow(
                        () -> new RuntimeException("Refresh token not found")
                );
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        userCredentialServiceImpl.validateToken(token);
        return "Token is valid";
    }

    @GetMapping("/getUserId/{userName}")
    public Long getUserId(@PathVariable String userName) {
        return userCredentialService.getUserId(userName);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userCredentialService.deleteUser(userId);
        return "user deleted successfully";
    }

    @GetMapping("/getUserByName/{name}")
    public UserResponse getUserByName(@PathVariable String name) {
        return userCredentialService.getUserByName(name);
    }

    @GetMapping("/getAllUser")
    public List<UserResponse> getAllUser() {
        return userCredentialService.getAllUser();
    }

}
