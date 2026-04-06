package com.app.mailmanager.service.impl;

import com.app.mailmanager.integration.gmail.GmailAdapter;
import com.app.mailmanager.service.api.EmailService;
import com.app.mailmanager.service.model.EmailInternal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación de la lógica de negocio de MailManager.
 * Orquestador N-Tier que delega en la capa de integración.
 */
@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private GmailAdapter gmailAdapter;

    @Override
    public String send(EmailInternal email) {
        logger.info("EVENT_TYPE=BUSINESS_LOGIC: Procesando solicitud de envío para {}", email.to());
        
        // Delegación a la capa de integración (Aislamiento del SDK)
        String messageId = gmailAdapter.sendRawEmail(email.to(), email.subject(), email.body());
        
        logger.info("EVENT_TYPE=BUSINESS_LOGIC: Envío completado con éxito. Provider ID: {}", messageId);
        return messageId;
    }
}
