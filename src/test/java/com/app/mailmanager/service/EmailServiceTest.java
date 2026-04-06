package com.app.mailmanager.service;

import com.app.mailmanager.integration.gmail.GmailAdapter;
import com.app.mailmanager.service.impl.EmailServiceImpl;
import com.app.mailmanager.service.model.EmailInternal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private GmailAdapter gmailAdapter;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    void whenSendEmail_thenDelegacyToAdapter() {
        org.slf4j.MDC.put("correlation_id", "UNIT-TEST-CORRELATION-ID");
        try {
            EmailInternal email = new EmailInternal("dest@mail.com", "Subj", "Body");
            when(gmailAdapter.sendRawEmail(anyString(), anyString(), anyString())).thenReturn("msg-123");

            String result = emailService.send(email);

            assertThat(result).isEqualTo("msg-123");
            verify(gmailAdapter).sendRawEmail("dest@mail.com", "Subj", "Body");
        } finally {
            org.slf4j.MDC.remove("correlation_id");
        }
    }
}
