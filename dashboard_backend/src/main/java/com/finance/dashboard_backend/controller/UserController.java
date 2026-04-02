package com.finance.dashboard_backend.controller;
import com.finance.dashboard_backend.enums.Role;
import com.finance.dashboard_backend.model.User;
import com.finance.dashboard_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") // Sirf Admin hi users manage kar sakta hai
public class UserController {
    private final UserRepository userRepository;
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> toggleUserStatus(@PathVariable Long id, @RequestParam boolean active) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(active);
        userRepository.save(user);
        return ResponseEntity.ok("User status updated to: " + (active ? "Active" : "Inactive"));
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userRepository.findById(id).orElseThrow());
    }
    @PatchMapping("/{id}/role")
    public ResponseEntity<User> updateRole(@PathVariable Long id, @RequestParam Role role) {
        User user = userRepository.findById(id).orElseThrow();
        user.setRole(role);
        return ResponseEntity.ok(userRepository.save(user));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}