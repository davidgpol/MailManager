package com.app.mailmanager.integration.gmail;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.UserCredentials;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

/**
 * Implementación industrial nativa del adaptador de Gmail.
 * Utiliza el SDK de Google de forma autónoma con UserCredentials,
 * eliminando la dependencia de la persistencia JDBC y Spring Security OAuth2.
 */
@Service
public class GmailAdapterImpl implements GmailAdapter {

    private static final Logger logger = LoggerFactory.getLogger(GmailAdapterImpl.class);
    private static final String APPLICATION_NAME = "MailManager";

    @Value("${GOOGLE_CLIENT_ID}")
    private String clientId;

    @Value("${GOOGLE_CLIENT_SECRET}")
    private String clientSecret;

    @Value("${GOOGLE_REFRESH_TOKEN}")
    private String refreshToken;

    @Value("${SENDER_EMAIL}")
    private String senderEmail;

    @Override
    public String sendRawEmail(String recipient, String subject, String body) {
        logger.info("EVENT_TYPE=GMAIL_INTEGRATION: Preparando envio desde {} hacia {}", senderEmail, recipient);

        try {
            // 1. Inicializar credenciales nativas (Google se encarga del refresco)
            GoogleCredentials credentials = UserCredentials.newBuilder()
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setRefreshToken(refreshToken)
                    .build();

            // 2. Construir el cliente de Gmail
            Gmail service = new Gmail.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    new HttpCredentialsAdapter(credentials))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            // 3. Crear y enviar el mensaje MIME
            MimeMessage mimeMessage = createMimeMessage(recipient, subject, body);
            Message message = createMessageWithEmail(mimeMessage);

            message = service.users().messages().send("me", message).execute();
            
            logger.info("EVENT_TYPE=GMAIL_INTEGRATION: Exito rotundo. Google ID: {}", message.getId());
            return message.getId();

        } catch (Exception e) {
            logger.error("EVENT_TYPE=GMAIL_INTEGRATION: Fallo tecnico con Google SDK: {}", e.getMessage(), e);
            throw new RuntimeException("Error en Google SDK Nativo", e);
        }
    }

    private MimeMessage createMimeMessage(String to, String subject, String bodyText) throws Exception {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(senderEmail));
        email.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    private Message createMessageWithEmail(MimeMessage emailContent) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        String encodedEmail = Base64.encodeBase64URLSafeString(buffer.toByteArray());
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
}
