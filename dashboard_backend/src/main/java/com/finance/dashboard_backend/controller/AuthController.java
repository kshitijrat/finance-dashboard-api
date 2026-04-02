package com.finance.dashboard_backend.controller;
import com.finance.dashboard_backend.dto.request.LoginRequest;
import com.finance.dashboard_backend.dto.request.RegisterRequest;
import com.finance.dashboard_backend.dto.response.AuthResponse;
import com.finance.dashboard_backend.security.UserDetailsImpl;
import com.finance.dashboard_backend.service.AuthService;
import com.finance.dashboard_backend.service.RefreshTokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    @GetMapping("/me")
    public ResponseEntity<AuthResponse> getMe() {
        return ResponseEntity.ok(authService.getMyProfile());
    }
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody Map<String, String> request) {
        String requestToken = request.get("refreshToken");
        return ResponseEntity.ok(authService.refreshToken(requestToken));
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        refreshTokenService.deleteByUserId(userDetails.getUser().getId());
        return ResponseEntity.ok("Log out successful!");
    }
}