package com.example.administrator.service;

import com.example.administrator.dto.AdministratorDto;
import com.example.administrator.entity.Administrator;

public interface AdministratorService {


    Administrator registerAdministrator(AdministratorDto administratorDto);

    Administrator getAdministratorByEmail(String email);

    void saveAdministrator(Administrator administrator);
}
