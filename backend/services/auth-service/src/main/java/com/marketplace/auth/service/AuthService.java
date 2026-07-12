package com.marketplace.auth.service;

import com.marketplace.auth.dto.request.LoginRequest;
import com.marketplace.auth.dto.request.LogoutRequest;
import com.marketplace.auth.dto.request.RefreshTokenRequest;
import com.marketplace.auth.dto.request.RegisterRequest;
import com.marketplace.auth.dto.response.*;
import com.marketplace.auth.entity.RefreshToken;
import com.marketplace.auth.entity.Role;
import com.marketplace.auth.entity.User;
import com.marketplace.auth.exception.EmailAlreadyExistsException;
import com.marketplace.auth.exception.InvalidCredentialsException;
import com.marketplace.auth.exception.InvalidRefreshTokenException;
import com.marketplace.auth.repository.RefreshTokenRepository;
import com.marketplace.auth.repository.UserRepository;
import com.marketplace.auth.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .enabled(true)
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        return RegisterResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .fullName(savedUser.getFullName())
                .message("User registered successfully")
                .build();
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        refreshTokenRepository.findByUser(user)
                .ifPresent(token -> {
                    System.out.println("Deleting old refresh token...");
                    refreshTokenRepository.delete(token);
                    refreshTokenRepository.flush();
                });

        RefreshToken savedRefreshToken = RefreshToken.builder()
                .token(refreshToken)
                .user(user)
                .expiryDate(
                        LocalDateTime.now()
                                .plusSeconds(jwtService.getRefreshTokenExpiration() / 1000)
                )
                .build();

        refreshTokenRepository.save(savedRefreshToken);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtService.getAccessTokenExpiration() / 1000)
                .message("Login successful")
                .build();
    }

    @Transactional(readOnly = true)
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(InvalidRefreshTokenException::new);

        if (jwtService.isTokenExpired(refreshToken.getToken())) {
            throw new InvalidRefreshTokenException();
        }

        User user = refreshToken.getUser();

        String accessToken = jwtService.generateAccessToken(user);

        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(jwtService.getAccessTokenExpiration() / 1000)
                .build();
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getCurrentUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }

    @Transactional
    public LogoutResponse logout(LogoutRequest request) {

        RefreshToken refreshToken =
                refreshTokenRepository.findByToken(request.getRefreshToken())
                        .orElseThrow(InvalidRefreshTokenException::new);

        refreshTokenRepository.delete(refreshToken);

        return LogoutResponse.builder()
                .message("Logout successful")
                .build();
    }
}
