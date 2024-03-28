package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.dto.UserLoginDTO;
import com.openclassrooms.mddapi.dto.UserRegisterDTO;
import com.openclassrooms.mddapi.dto.UserUpdateDTO;
import com.openclassrooms.mddapi.models.User;

public interface AuthService {

    UserDTO register(UserRegisterDTO registerDTO);
    UserDTO login(UserLoginDTO loginDTO);
    UserDTO updateUser(UserUpdateDTO updateDTO);
}