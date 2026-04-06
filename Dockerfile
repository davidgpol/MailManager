# Etapa de construcción
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY docs/specs/openapi.yaml docs/specs/openapi.yaml
# Descargar dependencias (cache)
RUN mvn dependency:go-offline -B
COPY src ./src
# Generar código y compilar (Bytecode 21 / Runtime compatible)
RUN mvn package -DskipTests

# Etapa de ejecución (Alineada con volúmenes compartidos)
FROM oraclelinux:9-slim
RUN microdnf install -y java-21-openjdk-headless && microdnf clean all

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Carpeta para la base de datos H2 persistente
RUN mkdir -p /app/data

# Ejecutamos como ROOT para compatibilidad con el host (David's requirement)
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8081", "--spring.datasource.url=jdbc:h2:file:/app/data/mailmanager_db"]
