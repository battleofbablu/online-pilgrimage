package com.example.auth.dto;

import com.example.auth.entity.Role;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
}
