package com.app.mailmanager.api.exception;

import com.app.mailmanager.AbstractBaseIntegrationTest;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MdcTraceabilityTest extends AbstractBaseIntegrationTest {

    @Test
    void whenRequestReceived_thenResponseIncludesCorrelationId() throws Exception {
        String testCorrelationId = "DAVID-TRACE-STABLE";

        // Verifica que el filtro MDC funciona correctamente devolviendo el Header
        mockMvc.perform(get("/actuator/health")
                .header("X-Correlation-ID", testCorrelationId))
                .andDo(result -> {
                    org.slf4j.LoggerFactory.getLogger(MdcTraceabilityTest.class)
                        .error("EVIDENCIA_LOG_JSON: Verificando MDC en la peticion {}", testCorrelationId);
                })
                .andExpect(status().isOk())
                .andExpect(header().string("X-Correlation-ID", testCorrelationId));
    }
}
