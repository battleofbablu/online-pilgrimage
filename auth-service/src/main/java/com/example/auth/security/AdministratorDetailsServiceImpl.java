package com.example.auth.security;

import com.example.auth.entity.Administrator;
import com.example.auth.feign.AdministratorClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdministratorDetailsServiceImpl implements UserDetailsService {

    private final AdministratorClient administratorClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Map<String, String> payload = Map.of("email", email);
        Administrator administrator = administratorClient.getAdministratorByEmail(payload);

        if (administrator == null) {
            throw new UsernameNotFoundException("Administrator not found with email: " + email);
        }

        return new CustomUserDetails(administrator);
    }
}
