package com.app.mailmanager.service.api;

import com.app.mailmanager.service.model.EmailInternal;

/**
 * Contrato de la capa de negocio para la gestión de correos (Regla LAYER_01).
 */
public interface EmailService {
    /**
     * Procesa y envía un correo electrónico.
     * @param email Datos del correo.
     * @return ID del mensaje generado por el proveedor.
     */
    String send(EmailInternal email);
}
