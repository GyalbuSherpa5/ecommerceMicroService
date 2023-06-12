package com.don.userservice.controller;

import com.don.userservice.dto.UserLogin;
import com.don.userservice.model.UserCredential;
import com.don.userservice.service.UserCredentialService;
import com.don.userservice.service.UserCredentialServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserCredentialController {
    private final UserCredentialService userCredentialService;
    private final UserCredentialServiceImpl userCredentialServiceImpl;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String saveUser(@RequestBody UserCredential userCredential) {
        userCredentialService.saveUser(userCredential);
        return "User saved successfully";
    }
    @PutMapping("/update/{userId}")
    public String updateUser(@RequestBody UserCredential userCredential,@PathVariable Long userId){
        userCredentialService.updateUser(userCredential,userId);
        return "User updated successfully";
    }

    @PostMapping("/login")
    public String getToken(@RequestBody UserLogin userLogin) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLogin.getUserName(),
                        userLogin.getPassword()
                )
        );

        if (authenticate.isAuthenticated()) {
            return userCredentialServiceImpl.generateToken(userLogin.getUserName());
        } else {
            throw new RuntimeException("Invalid Access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        userCredentialServiceImpl.validateToken(token);
        return "Token is valid";
    }
    @GetMapping("/getUserId/{userName}")
    public Long getUserId(@PathVariable String userName){
        return userCredentialService.getUserId(userName);
    }
}
