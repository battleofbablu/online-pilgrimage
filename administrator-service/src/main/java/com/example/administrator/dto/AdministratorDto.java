package com.example.administrator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdministratorDto {

    private String name;
    private String email;
    private String password;
    private String role;
}
