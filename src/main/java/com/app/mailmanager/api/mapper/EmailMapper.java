package com.app.mailmanager.api.mapper;

import com.app.mailmanager.api.generated.model.EmailRequest;
import com.app.mailmanager.service.model.EmailInternal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper para desacoplar los DTOs de la API de los Records de Servicio.
 * Cumple con la Regla LAYER_02.
 */
@Mapper(componentModel = "spring")
public interface EmailMapper {

    @Mapping(target = "to", source = "to")
    @Mapping(target = "subject", source = "subject")
    @Mapping(target = "body", source = "body")
    EmailInternal toInternal(EmailRequest request);
}
