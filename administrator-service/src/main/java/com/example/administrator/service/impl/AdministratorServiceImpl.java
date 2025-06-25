package com.example.administrator.service.impl;

import com.example.administrator.dto.AdministratorDto;
import com.example.administrator.entity.Administrator;
import com.example.administrator.repository.AdministratorRepository;
import com.example.administrator.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorRepository administratorRepository;


    @Override
    public Administrator registerAdministrator(AdministratorDto dto)
    {
        Administrator administrator = Administrator.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
        return administratorRepository.save(administrator);
    }

    @Override
    public Administrator getAdministratorByEmail(String email) {
        return administratorRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Administrator not found"));
    }

    @Override
    public void saveAdministrator(Administrator administrator) {
        administratorRepository.save(administrator);

    }
}
