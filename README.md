# 📧 MailManager Microservice

MailManager es un microservicio API-First diseñado para la gestión segura e industrial de envíos de correo corporativo utilizando el SDK nativo de Google Gmail. Construido bajo una arquitectura N-Tier Desacoplada y con un fuerte enfoque en la observabilidad y el rendimiento.

## 🚀 Stack Técnico
* **Lenguaje**: Java 25 (LTS) con Virtual Threads (Project Loom) habilitados para I/O.
* **Framework**: Spring Boot 3.4.1.
* **Seguridad**: Stateless JWT (Servidor de Recursos) y Google OAuth2 nativo (Headless Mode).
* **Contrato API**: OpenAPI 3.1.0 (`docs/specs/openapi.yaml`).
* **Transformación**: MapStruct (Conversión limpia entre DTOs y Records).
* **Contenedores**: Docker (Oracle Linux 9 + Java 25).
* **Logs**: JSON ECS con Mapped Diagnostic Context (MDC) para trazabilidad E2E.

## 🛡️ Arquitectura N-Tier Desacoplada
El proyecto sigue una estricta separación de capas (Layers Purity):
1. **API (`api/`)**: Expone el contrato, aplica seguridad JWT y transforma DTOs.
2. **Business (`service/`)**: Lógica pura orquestada con Java 25 Records inmutables.
3. **Integration (`integration/`)**: Adaptadores externos que encapsulan dependencias (ej. Google SDK) para evitar la contaminación del dominio.

## 🔑 Configuración de Seguridad (Google OAuth2)

Este microservicio opera en modo **Server-to-Server (Headless)**. Requiere un `Refresh Token` válido de Google para operar de forma autónoma sin intervención humana ni sesiones de navegador.

Para desplegar el servicio, debes proporcionar las siguientes variables de entorno:

```env
GOOGLE_CLIENT_ID=tu_client_id.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=tu_client_secret
GOOGLE_REFRESH_TOKEN=tu_refresh_token_autorizado
SENDER_EMAIL=tu_correo_autorizado@gmail.com
```

## 🐳 Despliegue en Producción (Docker)

```bash
# Construir la imagen
docker build -t mailmanager:latest .

# Ejecutar el contenedor inyectando los secretos
docker run -d --name mailmanager-production \
  -e GOOGLE_CLIENT_ID="tu-client-id" \
  -e GOOGLE_CLIENT_SECRET="tu-client-secret" \
  -e GOOGLE_REFRESH_TOKEN="tu-refresh-token" \
  -e SENDER_EMAIL="tu-correo@gmail.com" \
  -p 8080:8081 \
  mailmanager:latest
```

## 📖 Documentación de Integración
Para aprender a consumir esta API desde otros microservicios o Postman (incluyendo la autenticación JWT), consulta la [Guía de Integración](docs/INTEGRATION_GUIDE.md) y la [Colección de Postman](docs/postman/MailManager.postman_collection.json).

## 🧠 Memoria Institucional
Las decisiones arquitectónicas, las lecciones aprendidas y el estado del roadmap se encuentran versionadas en el directorio `.ai/` como parte del ADN del proyecto.
