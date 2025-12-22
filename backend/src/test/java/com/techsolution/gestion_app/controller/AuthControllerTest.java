package com.techsolution.gestion_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsolution.gestion_app.domain.entities.User;
import com.techsolution.gestion_app.service.AuthService;
import com.techsolution.gestion_app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthService authService;

    @Test
    void registerNewUserReturnsOk() throws Exception {
        User u = new User();
        u.setUsername("nuevo");
        u.setPassword("123");

        when(userRepository.findByUsername("nuevo")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(u);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(u)))
                .andExpect(status().isOk())
                .andExpect(content().string("Registrado"));
    }

    @Test
    void loginWithValidCredentialsReturnsToken() throws Exception {
        User u = new User();
        u.setUsername("juan");
        u.setPassword("123");
        when(authService.login("juan", "123")).thenReturn("TOK");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(u)))
                .andExpect(status().isOk())
                .andExpect(content().string("TOK"));
    }

    @Test
    void loginWithInvalidCredentialsReturns401() throws Exception {
        User u = new User();
        u.setUsername("juan");
        u.setPassword("bad");
        when(authService.login("juan", "bad")).thenReturn(null);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(u)))
                .andExpect(status().isUnauthorized());
    }
}
