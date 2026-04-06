# 🗺️ MailManager Structure Map (Priority 3)

## 📦 Base Package: `com.app.mailmanager`

### 1. API Layer (`/src/main/java/com/app/mailmanager/api`)
- `controller/`: REST Controllers implementing `mailmanager-api` interfaces.
- `generated/`: Interfaces and DTOs created from `openapi.yaml`.
- `exception/`: Global exception handling logic.
- `mapper/`: API DTO <-> Service Record converters.

### 2. Service Layer (`/src/main/java/com/app/mailmanager/service`)
- `api/`: Service interfaces.
- `impl/`: Business logic implementations.
- `model/`: Internal Java 25 Records.

### 3. Integration Layer (`/src/main/java/com/app/mailmanager/integration`)
- `gmail/`: Gmail API SDK adapters.
- `security/`: OAuth2, PKCE, and JWT configuration.
- `config/`: Spring beans and infrastructure config.

---

## 📂 Documentation & Design (`/docs`)
- `architecture/`: Architecture diagrams (C4, UML).
- `postman/`: Postman Collections for MailManager API.
- `specs/`: `openapi.yaml` contract.
- `INTEGRATION_GUIDE.md`: Guía oficial de integración para sistemas consumidores (JWT auth & cURL examples).