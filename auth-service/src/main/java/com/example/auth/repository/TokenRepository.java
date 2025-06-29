package com.example.auth.repository;

import com.example.auth.entity.Role;
import com.example.auth.entity.Token;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Modifying
    @Query("DELETE FROM Token t WHERE t.refreshToken IS NOT NULL AND t.user.email = :email AND t.user.role = :role")
    void deleteUserTokens(@Param("email") String email, @Param("role") Role role);

    @Modifying
    @Query("DELETE FROM Token t WHERE t.refreshToken IS NOT NULL AND t.admin.email = :email AND t.admin.role = :role")
    void deleteAdminTokens(@Param("email") String email, @Param("role") Role role);

    @Modifying
    @Query("DELETE FROM Token t WHERE t.refreshToken IS NOT NULL AND t.administrator.email = :email AND t.administrator.role = :role")
    void deleteAdministratorTokens(@Param("email") String email, @Param("role") Role role);
}
