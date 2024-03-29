package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.dto.UserLoginRequestDTO;
import com.openclassrooms.mddapi.dto.UserRegisterDTO;
import com.openclassrooms.mddapi.dto.UserUpdateDTO;

public interface AuthService {

    UserDTO register(UserRegisterDTO registerDTO);
    UserDTO login(UserLoginRequestDTO loginDTO);
    UserDTO updateUser(UserUpdateDTO updateDTO);
}