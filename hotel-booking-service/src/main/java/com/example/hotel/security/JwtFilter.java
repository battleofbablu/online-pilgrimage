package com.example.hotel.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String authHeader = request.getHeader("Authorization");

        System.out.println("🔍 JwtFilter triggered for path: " + path);
        System.out.println("🪪 Authorization Header: " + authHeader);

        // Allow-list certain paths
        if (path.startsWith("/api/auth")
                || path.startsWith("/api/hotels/internal")
                || path.startsWith("/actuator")) {
            System.out.println("✅ Public path matched, skipping token validation");
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.err.println("❌ Missing or malformed Authorization header");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7); // Strip "Bearer "
        System.out.println("🔐 Token extracted: " + token);

        try {
            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);

            System.out.println("✅ Parsed Token - Email: " + email + ", Role: " + role);

            if (email == null || role == null) {
                System.err.println("❌ Email or Role is null from token");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token: missing claims");
                return;
            }

            // If already authenticated, skip
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                System.out.println("ℹ️ SecurityContext already set, skipping");
                filterChain.doFilter(request, response);
                return;
            }

            List<SimpleGrantedAuthority> authorities =
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(email, null, authorities);

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

            System.out.println("🔒 Security context set for: " + email + " with role: ROLE_" + role);

        } catch (Exception ex) {
            System.err.println("❌ Exception in token processing: " + ex.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token: " + ex.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
