package com.app.mailmanager.api.controller;

import com.app.mailmanager.AbstractBaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Test de validación final para la Fase 4 (GREEN Phase).
 * Valida el flujo completo API -> Service -> Integration con seguridad simulada.
 */
class EmailApiIntegrityTest extends AbstractBaseIntegrationTest {

    @Test
    void whenPostEmailWithValidToken_thenReturnsAccepted() throws Exception {
        String jsonRequest = """
                {
                  "to": "davidgapol@gmail.com",
                  "subject": "MailManager Securizado - Confirmación Final",
                  "body": "El microservicio está operando correctamente bajo Java 25 y seguridad JWT. Envío desde test de integración."
                }
                """;

        mockMvc.perform(post("/api/v1/emails/send")
                .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_mail.write"))) // Simulación de seguridad
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("SENT"))
                .andExpect(jsonPath("$.messageId").exists());
    }

    @Test
    void whenPostEmailWithoutToken_thenReturnsUnauthorized() throws Exception {
        mockMvc.perform(post("/api/v1/emails/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isUnauthorized());
    }
}
