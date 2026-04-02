package com.finance.dashboard_backend.security;
import com.finance.dashboard_backend.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final User user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // "ROLE_ADMIN", "ROLE_VIEWER" format Spring Security ke roles ke liye standard hai
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }
    @Override
    public String getPassword() { return user.getPassword(); }
    @Override
    public String getUsername() { return user.getEmail(); }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return user.isActive(); }
    public User getUser() { return this.user; }
}