package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.dto.UserLoginDTO;
import com.openclassrooms.mddapi.dto.UserRegisterDTO;
import com.openclassrooms.mddapi.dto.UserUpdateDTO;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO register(UserRegisterDTO registerDTO) {
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword())); // Crypter le mot de passe
        user.setCreated_at(new Date());
        user.setUpdated_at(new Date());
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public UserDTO login(UserLoginDTO loginDTO) {
        // La logique de connexion serait gérée ici, probablement en utilisant Spring Security pour authentifier l'utilisateur
        // Cet exemple retourne un UserDTO simplifié pour l'illustration
        return new UserDTO();
    }

    @Override
    public UserDTO updateUser(UserUpdateDTO updateDTO) {
        User existingUser = userRepository.findById(updateDTO.getId()).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        existingUser.setEmail(updateDTO.getEmail());
        existingUser.setUsername(updateDTO.getUsername());

        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        }
        existingUser.setUpdated_at(new Date());
        User updatedUser = userRepository.save(existingUser);
        return convertToDTO(updatedUser);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setCreatedAt(user.getCreated_at());
        dto.setUpdatedAt(user.getUpdated_at());
        return dto;
    }
}
