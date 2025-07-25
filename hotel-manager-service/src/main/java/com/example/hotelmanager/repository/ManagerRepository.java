package com.example.hotelmanager.repository;

import com.example.hotelmanager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for accessing Manager data.
 */
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    /**
     * Finds a manager by their email.
     * @param email manager's email
     * @return Optional of Manager
     */
    Optional<Manager> findByEmail(String email);

    /**
     * Checks if a manager exists with the given email.
     * @param email manager email
     * @return true if exists
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a manager exists with the given manager ID.
     * @param managerId manager ID
     * @return true if exists
     */
    boolean existsByManagerId(String managerId);
}
