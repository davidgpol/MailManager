package com.app.mailmanager.api.exception;

import com.app.mailmanager.api.generated.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

/**
 * Gestor global de excepciones para MailManager.
 * Garantiza el cumplimiento de la Regla API_02 (Standardized Error Response).
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, HttpServletRequest request) {
        logger.error("EVENT_TYPE=API_ERROR: Error no controlado detectado: {}", ex.getMessage(), ex);

        ErrorResponse error = new ErrorResponse(
                OffsetDateTime.now(),
                "INTERNAL_SERVER_ERROR",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(Exception ex, HttpServletRequest request) {
        logger.warn("EVENT_TYPE=API_ERROR: Fallo de validacion en la peticion: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                OffsetDateTime.now(),
                "VALIDATION_ERROR",
                "Uno o mas campos de la solicitud son invalidos",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
