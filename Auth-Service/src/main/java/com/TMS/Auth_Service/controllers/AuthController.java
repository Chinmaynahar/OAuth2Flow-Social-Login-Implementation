package com.TMS.Auth_Service.controllers;

import com.TMS.Auth_Service.models.dtos.requests.UserRequest;
import com.TMS.Auth_Service.models.entities.RefreshToken;
import com.TMS.Auth_Service.models.entities.User;
import com.TMS.Auth_Service.repository.UserRepository;
import com.TMS.Auth_Service.services.RefreshTokenService;
import com.TMS.Auth_Service.services.SignUpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {



    private final SignUpService signUpService;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;

    public AuthController(SignUpService signUpService, RefreshTokenService refreshTokenService, UserRepository userRepository) {
        this.signUpService = signUpService;
        this.refreshTokenService = refreshTokenService;
        this.userRepository = userRepository;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam("refresh_token") String refreshToken) {
        refreshTokenService.deleteRefreshToken(refreshToken);
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRequest request) {
        signUpService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully");
    }
}

