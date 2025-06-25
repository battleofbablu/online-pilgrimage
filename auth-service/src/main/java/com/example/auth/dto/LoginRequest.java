package com.example.auth.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    private String email;
    private String password;
    private String loginType; // Either "USER" or "ADMIN"
}
