# 🔌 Guía de Integración: MailManager API

El microservicio **MailManager** proporciona una interfaz estandarizada y segura para el envío de correos corporativos. Está diseñado bajo los principios de API-First, Arquitectura N-Tier Desacoplada y Seguridad Zero-Trust.

## 🔒 Arquitectura de Seguridad (JWT Stateless)

La API de MailManager está protegida mediante un **Servidor de Recursos OAuth2**. No gestiona sesiones (Stateless) ni acepta credenciales básicas. 

Para consumir cualquier endpoint de negocio (ej. `/api/v1/emails/send`), el sistema consumidor **debe** proveer un token JWT válido firmado por la autoridad emisora confiable (por defecto, Google) y que contenga los permisos necesarios.

### Requisitos del Token JWT
1. **Issuer (`iss`)**: Debe coincidir con el configurado en la propiedad `spring.security.oauth2.resourceserver.jwt.issuer-uri` (ej. `https://accounts.google.com`).
2. **Audience (`aud`)**: Debe incluir el ID de cliente autorizado para MailManager.
3. **Scopes**: El token debe incluir el permiso (authority) `SCOPE_mail.write` para poder ejecutar operaciones de envío.
4. **Vigencia (`exp`)**: El token no debe estar caducado.

---

## 🚀 Consumiendo la API (Client-to-Server)

### Paso 1: Obtención del Token
Dependiendo de la arquitectura de tu empresa, el microservicio consumidor deberá negociar un token (vía *Client Credentials Grant* o *Authorization Code* si actúa en nombre de un usuario) con el Identity Provider corporativo.

*(Nota para pruebas locales: Utiliza la colección de Postman ubicada en `docs/postman/` que tiene el flujo de autorización pre-configurado para facilitarte la obtención manual del token).*

### Paso 2: Petición HTTP
Una vez obtenido el JWT, inyéctalo en la cabecera `Authorization` de tu petición.

Es **altamente recomendable** enviar la cabecera `X-Correlation-ID`. Si no se envía, MailManager generará un UUID interno automáticamente para la trazabilidad de los logs (Regla LOG_02), pero pasar tu propio ID permite trazar la petición a través de múltiples microservicios.

#### Ejemplo cURL:

```bash
curl -X POST http://mailmanager-host:8080/api/v1/emails/send \
  -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIs..." \
  -H "Content-Type: application/json" \
  -H "X-Correlation-ID: APP-CONSUMER-12345" \
  -d '{
    "to": "cliente@dominio.com",
    "subject": "Factura Adjunta",
    "body": "Estimado cliente, adjuntamos su factura mensual..."
  }'
```

---

## 📥 Gestión de Respuestas y Errores

MailManager sigue estrictamente el estándar de errores corporativo (Regla `API_02`). Debes preparar tu cliente para manejar los siguientes escenarios:

### 🟢 Éxito (HTTP 202 Accepted)
Indica que el correo ha sido procesado por la lógica de negocio y delegado con éxito al proveedor subyacente (ej. Gmail SDK).
```json
{
  "messageId": "19d497b784562158",
  "status": "SENT",
  "timestamp": "2026-04-01T16:38:42.066Z"
}
```

### 🔴 Error de Seguridad (HTTP 401 / 403)
Ocurre si no se envía el token, si el token es inválido/caducado (`401 Unauthorized`), o si el token es válido pero carece del scope necesario (`403 Forbidden`).

### 🔴 Error de Validación o Negocio (HTTP 400)
El formato de respuesta de error siempre será este esquema estándar. Debes programar tu cliente para deserializar este objeto cuando recibas un código 4xx o 5xx.
```json
{
  "timestamp": "2026-04-01T16:40:00.000Z",
  "code": "VALIDATION_ERROR",
  "message": "Uno o mas campos de la solicitud son invalidos",
  "path": "/api/v1/emails/send"
}
```