package com.techsolution.gestion_app.service;

import com.techsolution.gestion_app.domain.entities.User;
import com.techsolution.gestion_app.repository.UserRepository;
import com.techsolution.gestion_app.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    private AuthService authService;

    @BeforeEach
    void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        jwtUtil = Mockito.mock(JwtUtil.class);
        authService = new AuthService(userRepository, jwtUtil);
    }

    @Test
    void loginSuccessReturnsToken() {
        User u = new User();
        u.setUsername("juan");
        u.setPassword("1234");
        u.setRoles(new HashSet<>());

        when(userRepository.findByUsername("juan")).thenReturn(Optional.of(u));
        when(jwtUtil.generateToken(anyString(), Mockito.any())).thenReturn("TOK123");

        String token = authService.login("juan", "1234");
        assertNotNull(token);
        assertEquals("TOK123", token);
    }

    @Test
    void loginWrongPasswordReturnsNull() {
        User u = new User();
        u.setUsername("juan");
        u.setPassword("1234");
        when(userRepository.findByUsername("juan")).thenReturn(Optional.of(u));

        String token = authService.login("juan", "wrong");
        assertNull(token);
    }

    @Test
    void loginNonExistingUserReturnsNull() {
        when(userRepository.findByUsername("someone")).thenReturn(Optional.empty());
        String token = authService.login("someone", "x");
        assertNull(token);
    }
}
