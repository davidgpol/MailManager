package com.app.mailmanager.api.generated.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/**
 * Record inmutable para solicitudes de envío de email.
 * Fuente de verdad: docs/specs/openapi.yaml
 */
public record EmailRequest(
    @NotNull @Email String to,
    @NotNull String subject,
    @NotNull String body
) {}
