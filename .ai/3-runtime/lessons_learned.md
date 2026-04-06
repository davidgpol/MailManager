# 🧠 MailManager | Lessons Learned

## 🕒 Session: 2026-04-01 | Industrialization Phase
- **Headless OAuth2**: Do not use `OAuth2AuthorizedClientManager` (Spring Security) for CLI/Server-to-Server tasks. It depends on browser sessions and user cookies. Use native `GoogleCredentials` with an injected Refresh Token for stable, autonomous background processing.
- **Records & OpenAPI**: The `spring` generator currently fails at creating valid Java 25 Records with Jakarta EE 10 compatibility. Use a Dual Strategy: Generate Interfaces (Contract), but implement DTOs and models manually as Records in the same package to guarantee [P0] compliance.
- **SQL Idempotency**: Database seeds in persistent Docker volumes MUST be idempotent. Use `MERGE INTO ... KEY(...)` with explicit column mapping instead of standard `INSERT` to avoid Primary Key violations on container restart.
- **Maven Output**: Maven Surefire intercepts and reformats JSON logs into text. Reliable validation of Structured Logging (JSON ECS) and MDC propagation MUST be done by inspecting real Docker logs or `spring-boot:run` output.
