package com.example.administrator.repository;

import com.example.administrator.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator,Long> {
    Optional<Administrator> findByEmail(String email);

}
