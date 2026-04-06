package com.app.mailmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import static org.assertj.core.api.Assertions.assertThat;

class MailManagerApplicationTests extends AbstractBaseIntegrationTest {

    @Autowired
    private Environment environment;

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring arranca sin errores
    }

    @Test
    void runningOnJava25_Runtime() {
        String javaVersion = System.getProperty("java.version");
        assertThat(javaVersion).startsWith("25");
    }

    @Test
    void virtualThreadsEnabled() {
        String virtualThreadsProperty = environment.getProperty("spring.threads.virtual.enabled");
        assertThat(virtualThreadsProperty).isEqualTo("true");
    }
}
