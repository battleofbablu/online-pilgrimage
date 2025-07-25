package com.example.hotel.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        //  Public/Internal endpoints (no auth required)
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/hotels/internal/**").permitAll()
                        .requestMatchers("/api/bookings/internal/**").permitAll()
                        .requestMatchers("/api/users/internal/**").permitAll()

                        //  Geoapify access (USER only)
                        .requestMatchers("/api/geoapify/**").hasRole("USER")
                        .requestMatchers("/api/geoapify/hotels/search").hasRole("USER")

                        //  Booking APIs
                        .requestMatchers("/api/bookings/manager/**").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers("/api/bookings/**").hasRole("USER")

                        //  Catch-all: Any other API requires authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    //  Expose AuthenticationManager bean for login if needed
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
