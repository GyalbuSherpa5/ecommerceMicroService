package com.don.userservice.service;

import com.don.userservice.model.RefreshToken;
import com.don.userservice.repository.RefreshTokenRepository;
import com.don.userservice.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserCredentialRepository userCredentialRepository;

    @Override
    public RefreshToken createRefreshToken(String userName) {
        RefreshToken refreshToken = RefreshToken.builder()
                .userCredential(userCredentialRepository.findByUserName(userName).get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken() +
                    "refresh token was expired, please sign in again");
        }
        return refreshToken;
    }
}
