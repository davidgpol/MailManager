package com.app.mailmanager.api.generated.model;

import java.time.OffsetDateTime;

/**
 * Record inmutable para respuestas de error estandarizadas (Regla API_02).
 */
public record ErrorResponse(
    OffsetDateTime timestamp,
    String code,
    String message,
    String path
) {}
