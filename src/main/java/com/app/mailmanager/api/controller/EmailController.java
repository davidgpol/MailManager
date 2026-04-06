package com.app.mailmanager.api.controller;

import com.app.mailmanager.api.generated.ApiApi;
import com.app.mailmanager.api.generated.model.EmailRequest;
import com.app.mailmanager.api.generated.model.EmailResponse;
import com.app.mailmanager.api.mapper.EmailMapper;
import com.app.mailmanager.service.api.EmailService;
import com.app.mailmanager.service.model.EmailInternal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

/**
 * Controlador REST para la gestión de correos.
 * Implementa el contrato API-First (Regla API_01).
 */
@RestController
public class EmailController implements ApiApi {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailMapper emailMapper;

    @Override
    public ResponseEntity<EmailResponse> sendEmail(EmailRequest emailRequest) {
        logger.info("EVENT_TYPE=API_ENTRY: Recibida peticion de envio de correo para {}", emailRequest.to());

        // Transformacion de DTO a Record de Negocio (Regla LAYER_02)
        EmailInternal internalEmail = emailMapper.toInternal(emailRequest);

        // Delegacion al Servicio (Regla LAYER_01)
        String messageId = emailService.send(internalEmail);

        EmailResponse response = new EmailResponse(
                messageId, 
                EmailResponse.StatusEnum.SENT, 
                OffsetDateTime.now()
        );

        logger.info("EVENT_TYPE=API_EXIT: Peticion de envio procesada correctamente. ID: {}", messageId);
        return ResponseEntity.accepted().body(response);
    }
}
