package com.example.auth.security;
import com.example.auth.entity.Admin;
import com.example.auth.feign.AdminClient;
import com.example.auth.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminDetailsServiceImpl implements UserDetailsService {

    private final AdminClient adminClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Map<String, String> payload = Map.of("email", email);
        Admin admin = adminClient.getAdminByEmail(payload);

        if (admin == null) {
            throw new UsernameNotFoundException("Admin not found with email: " + email);
        }

        return new CustomUserDetails(admin);
    }
}
