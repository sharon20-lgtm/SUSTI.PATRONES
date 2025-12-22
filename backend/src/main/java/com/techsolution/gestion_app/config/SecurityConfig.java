package com.techsolution.gestion_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final com.techsolution.gestion_app.security.JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(com.techsolution.gestion_app.security.JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/auth/**", "/h2-console/**", "/payments/**").permitAll()
            .anyRequest().authenticated();

        // JWT filter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        // allow frames (h2 console)
        http.headers().frameOptions().disable();
        return http.build();
    }
}
