package com.app.mailmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class MailManagerApplication {

    private static final Logger logger = LoggerFactory.getLogger(MailManagerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MailManagerApplication.class, args);
        logger.info("MailManagerApplication started successfully - Java 25 + Virtual Threads active.");
    }
}
