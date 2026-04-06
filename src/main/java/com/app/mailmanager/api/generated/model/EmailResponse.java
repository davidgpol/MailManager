package com.app.mailmanager.api.generated.model;

import java.time.OffsetDateTime;

/**
 * Record inmutable para respuestas de envío de email.
 */
public record EmailResponse(
    String messageId,
    StatusEnum status,
    OffsetDateTime timestamp
) {
    public enum StatusEnum {
        SENT, QUEUED, FAILED
    }
}
