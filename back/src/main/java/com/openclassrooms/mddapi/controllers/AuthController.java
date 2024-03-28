package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.dto.UserLoginDTO;
import com.openclassrooms.mddapi.dto.UserRegisterDTO;
import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Inscription
    @PostMapping("/register")
    @Operation(summary = "Register a new user", responses = {@ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class), examples = {@ExampleObject(name = "SuccessResponse", value = "{\"token\": \"your_generated_token_here\"}")})), @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class), examples = {@ExampleObject(name = "ErrorResponse", value = "{\"error\": \"Error message\"}")}))})
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserRegisterDTO registerDTO) {
        UserDTO registeredUser = authService.register(registerDTO);
        return ResponseEntity.ok(registeredUser);
    }

    // Login
    @PostMapping("/login")
    @Operation(summary = "Log in a user and return a JWT token", responses = {@ApiResponse(responseCode = "200", description = "User logged in successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserLoginDTO.class), examples = {@ExampleObject(name = "SuccessResponse", value = "{\"token\": \"your_generated_token_here\"}")})), @ApiResponse(responseCode = "401", description = "Invalid credentials or login error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class), examples = {@ExampleObject(name = "ErrorResponse", value = "{\"error\": \"Invalid credentials.\"}")}))})
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginDTO loginDTO, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto("error"));
        }
        try {
        UserDTO userDTO = authService.login(loginDTO);
        return ResponseEntity.ok().body(userDTO);
        } catch (
                AuthenticationException e) {                                              // Gère les exceptions d'authentification.

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }
    }


    // Mise à jour d'un utilisateur
    @PutMapping("/updateUser")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserUpdateDTO updateDTO) {
        UserDTO updatedUser = authService.updateUser(updateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    // Déconnexion
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok().body("Utilisateur déconnecté avec succès.");
    }
}
