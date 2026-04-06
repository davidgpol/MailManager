package com.app.mailmanager;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Clase base para todos los tests de integración de MailManager.
 * Proporciona el mock global de seguridad para evitar conexiones externas (SEC_05).
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class AbstractBaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected JwtDecoder jwtDecoder;
}
