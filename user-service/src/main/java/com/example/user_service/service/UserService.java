package com.example.user_service.service;

import com.example.user_service.dto.UserDto;
import com.example.user_service.entity.User;

public interface UserService {
    User registerUser(UserDto userDto);
    User getUserByEmail(String email);

    void saveUser(User user);
}