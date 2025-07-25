package com.example.hotelmanager.dto;

import com.example.hotelmanager.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ✅ DTO for manager authentication or basic manager details transfer
 * Can be used for internal microservice communication or data transfer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerDto {

    private String name;
    private String email;
    private String password;  // Should be kept secure and never exposed in response DTOs
    private Role role;        // Enum representing MANAGER, ADMIN, etc.
}
