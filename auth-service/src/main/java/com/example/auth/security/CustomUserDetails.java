package com.example.auth.security;

import com.example.auth.entity.Admin;
import com.example.auth.entity.Administrator;
import com.example.auth.entity.Role;
import com.example.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private String email;
    private String password;
    private String role;

    // Constructor for User
    public CustomUserDetails(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole().name(); // Assuming role is enum
    }

    // Constructor for Admin
    public CustomUserDetails(Admin admin) {
        this.email = admin.getEmail();
        this.password = admin.getPassword();
        this.role = admin.getRole().name();
    }
    public CustomUserDetails(Administrator administrator) {
        this.email = administrator.getEmail();
        this.password = administrator.getPassword();
        this.role = administrator.getRole().name();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override public String getUsername() { return email; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
