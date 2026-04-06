package com.app.mailmanager.integration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

/**
 * Configuración de Spring Security para MailManager (N-Tier Security Layer).
 * Implementa las reglas SEC_01 a SEC_10.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Regla SEC_06 (Stateless)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Regla SEC_05
            .authorizeHttpRequests(auth -> auth
                // Regla SEC_08 (Public Endpoints)
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/actuator/health/**").permitAll()
                .requestMatchers("/login/oauth2/code/**").permitAll() // Permitimos el callback de Google
                // Regla SEC_09 (Business Endpoints)
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())) // Servidor de Recursos JWT reactivado
            .oauth2Client(Customizer.withDefaults()); // Activo para el handshake con Google
            
        return http.build();
    }
}
