package com.app.mailmanager.integration.gmail;

/**
 * Adaptador para interactuar con la API de Gmail (Regla LAYER_01).
 * Esta interfaz es la única que la capa de servicio puede ver.
 */
public interface GmailAdapter {
    /**
     * Envía un correo electrónico de forma asíncrona/reactiva mediante Gmail API.
     */
    String sendRawEmail(String recipient, String subject, String body);
}
