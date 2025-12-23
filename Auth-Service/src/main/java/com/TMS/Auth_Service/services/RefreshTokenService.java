package com.TMS.Auth_Service.services;


import com.TMS.Auth_Service.models.entities.RefreshToken;
import com.TMS.Auth_Service.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {


    private final RefreshTokenRepository refreshTokenRepository;

    private static final int REFRESH_TOKEN_VALIDITY_DAYS = 7;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }


    @Transactional
    public RefreshToken createRefreshToken(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusDays(REFRESH_TOKEN_VALIDITY_DAYS);

        RefreshToken refreshToken = new RefreshToken(token, userId, expiresAt, now);

        RefreshToken savedToken = refreshTokenRepository.save(refreshToken);

        System.out.println("✅ Refresh token created for user ID: " + userId);
        System.out.println("Token: " + token.substring(0, 10) + "...");
        System.out.println("Expires at: " + expiresAt);

        return savedToken;
    }


    public RefreshToken verifyRefreshToken(String token) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(token);

        if (refreshTokenOpt.isEmpty()) {
            throw new RuntimeException("Refresh token not found");
        }

        RefreshToken refreshToken = refreshTokenOpt.get();

        if (refreshToken.isExpired()) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }


    @Transactional
    public void deleteRefreshToken(String token) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(token);
        refreshTokenOpt.ifPresent(refreshTokenRepository::delete);
    }


    @Transactional
    public void deleteRefreshTokensByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }


    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }


    public Optional<RefreshToken> findByUserId(Long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }


    public boolean isTokenValid(String token) {
        try {
            verifyRefreshToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
