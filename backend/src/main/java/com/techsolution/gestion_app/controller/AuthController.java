package com.techsolution.gestion_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.techsolution.gestion_app.domain.entities.User;
import com.techsolution.gestion_app.repository.UserRepository;

import java.util.HashSet;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final com.techsolution.gestion_app.service.AuthService authService;

    public AuthController(UserRepository userRepository, com.techsolution.gestion_app.service.AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Usuario ya existe");
        }
        if (user.getRoles() == null) user.setRoles(new HashSet<>());
        userRepository.save(user);
        return ResponseEntity.ok("Registrado");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        String token = authService.login(user.getUsername(), user.getPassword());
        if (token == null) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
        return ResponseEntity.ok(token);
    }
}
