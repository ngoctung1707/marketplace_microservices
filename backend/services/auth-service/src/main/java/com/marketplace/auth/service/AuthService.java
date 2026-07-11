package com.marketplace.auth.service;

import com.marketplace.auth.dto.request.LoginRequest;
import com.marketplace.auth.dto.request.RegisterRequest;
import com.marketplace.auth.dto.response.LoginResponse;
import com.marketplace.auth.dto.response.RegisterResponse;
import com.marketplace.auth.entity.Role;
import com.marketplace.auth.entity.User;
import com.marketplace.auth.exception.EmailAlreadyExistsException;
import com.marketplace.auth.exception.InvalidCredentialsException;
import com.marketplace.auth.repository.UserRepository;
import com.marketplace.auth.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

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

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = jwtService.generateAccessToken(user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(900L)
                .message("Login successful")
                .build();
    }
}
