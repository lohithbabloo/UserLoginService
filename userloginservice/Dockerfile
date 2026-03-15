# Build stage
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/JwtAuthentication.jar app.jar

# Environment variables (can be overridden)
ENV PORT=8080 \
    DB_URL=jdbc:postgresql://localhost:5432/userlogin \
    DB_USERNAME=postgres \
    DB_PASSWORD=db1234 \
    JWT_SECRET=5367566859703373367639792F423F452848284D6251655468576D5A71347437 \
    SPRING_JPA_HIBERNATE_DDL_AUTO=none

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

CMD ["java", "-jar", "app.jar"]
