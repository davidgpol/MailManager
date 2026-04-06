package com.app.mailmanager.service.model;

/**
 * Representación interna de un correo electrónico.
 * Implementado como Java 25 Record para garantizar inmutabilidad (Regla JAVA_01).
 */
public record EmailInternal(
    String to,
    String subject,
    String body
) {}
