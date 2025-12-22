package com.techsolution.gestion_app.service;

import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.domain.entities.User;
import com.techsolution.gestion_app.repository.UserRepository;
import com.techsolution.gestion_app.security.JwtUtil;

import java.util.HashSet;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            User user = userOpt.get();
            if (user.getRoles() == null) user.setRoles(new HashSet<>());
            return jwtUtil.generateToken(user.getUsername(), user.getRoles());
        }
        return null;
    }
}
