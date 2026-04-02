package com.finance.dashboard_backend.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.finance.dashboard_backend.dto.request.LoginRequest;
import com.finance.dashboard_backend.dto.request.RegisterRequest;
import com.finance.dashboard_backend.dto.response.AuthResponse;
import com.finance.dashboard_backend.enums.Role;
import com.finance.dashboard_backend.model.RefreshToken;
import com.finance.dashboard_backend.model.User;
import com.finance.dashboard_backend.repository.RefreshTokenRepository;
import com.finance.dashboard_backend.repository.UserRepository;
import com.finance.dashboard_backend.security.JwtService;
import com.finance.dashboard_backend.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final RefreshTokenRepository refreshTokenRepository;
        private final RefreshTokenService refreshTokenService;
        public AuthResponse register(RegisterRequest request) {
                // 1. Email check
                if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                        throw new RuntimeException("Email is already registered!");
                }

                // 2. User Create & Save
                var user = User.builder()
                                .name(request.getName())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(request.getRole() == null ? Role.VIEWER : request.getRole())
                                .isActive(true)
                                .build();

                User savedUser = userRepository.save(user); // Save to DB

                // 3. Tokens Generate
                var userDetails = new UserDetailsImpl(savedUser);
                String jwtToken = jwtService.generateToken(userDetails);

                // Refresh Token generate karna zaroori hai
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(savedUser.getId());

                // 4. Full Response Return
                return AuthResponse.builder()
                                .accessToken(jwtToken)
                                .refreshToken(refreshToken.getToken()) // Ab null nahi aayega
                                .email(savedUser.getEmail())
                                .name(savedUser.getName()) // Ab null nahi aayega
                                .role(savedUser.getRole().name())
                                .build();
        }

        // login method update
        public AuthResponse login(LoginRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

                var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
                var userDetails = new UserDetailsImpl(user);

                String jwtToken = jwtService.generateToken(userDetails);
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

                return AuthResponse.builder()
                                .accessToken(jwtToken)
                                .refreshToken(refreshToken.getToken()) // Refresh token add kiya
                                .email(user.getEmail())
                                .role(user.getRole().name())
                                .build();
        }

        // Naya Token generate karne ka method
        public AuthResponse refreshToken(String requestToken) {
                return refreshTokenRepository.findByToken(requestToken)
                                .map(refreshTokenService::verifyExpiration)
                                .map(RefreshToken::getUser)
                                .map(user -> {
                                        String token = jwtService.generateToken(new UserDetailsImpl(user));
                                        return AuthResponse.builder()
                                                        .accessToken(token)
                                                        .refreshToken(requestToken)
                                                        .email(user.getEmail())
                                                        .role(user.getRole().name())
                                                        .build();
                                })
                                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
        }

        public AuthResponse getMyProfile() {
                UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                                .getPrincipal();
                User user = userDetails.getUser();
                return AuthResponse.builder()
                                .email(user.getEmail())
                                .name(user.getName()) // AuthResponse mein 'name' field add kar dena
                                .role(user.getRole().name())
                                .build();
        }
}