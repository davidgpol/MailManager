package com.app.mailmanager.integration.security;

import com.app.mailmanager.AbstractBaseIntegrationTest;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SecurityIntegrityTest extends AbstractBaseIntegrationTest {

    @Test
    void whenAccessingBusinessEndpointWithoutToken_thenReturns401() throws Exception {
        // Regla SEC_09: Acceso denegado si no hay Bearer Token
        mockMvc.perform(post("/api/v1/emails/send"))
                .andExpect(status().isUnauthorized());
    }
}
