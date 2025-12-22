package com.techsolution.gestion_app.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void generateAndValidateToken() {
        String token = jwtUtil.generateToken("usuario1", Set.of("GERENTE","USER"));
        assertNotNull(token);
        assertTrue(jwtUtil.validateToken(token));
        String username = jwtUtil.getUsernameFromToken(token);
        assertEquals("usuario1", username);
        String roles = jwtUtil.getRolesFromToken(token);
        assertTrue(roles.contains("GERENTE"));
    }
}
