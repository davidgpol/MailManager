package com.app.mailmanager.api.mapper;

import com.app.mailmanager.api.generated.model.EmailRequest;
import com.app.mailmanager.service.model.EmailInternal;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class EmailMapperTest {

    private final EmailMapper mapper = Mappers.getMapper(EmailMapper.class);

    @Test
    void shouldMapEmailRequestToInternalRecord() {
        EmailRequest request = new EmailRequest("test@mail.com", "Asunto", "Cuerpo");
        
        EmailInternal result = mapper.toInternal(request);
        
        assertThat(result).isNotNull();
        assertThat(result.to()).isEqualTo("test@mail.com");
        assertThat(result.subject()).isEqualTo("Asunto");
        assertThat(result.body()).isEqualTo("Cuerpo");
    }
}
