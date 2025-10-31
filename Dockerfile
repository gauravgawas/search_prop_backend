# ---------- Stage 1: Build the JAR ----------
FROM eclipse-temurin:17-jdk-alpine AS builder

# Set working directory inside container
WORKDIR /app

# Copy Maven wrapper and pom.xml first (for caching dependencies)
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Download dependencies (helps caching)
RUN ./mvnw dependency:go-offline -B

# Copy actual source code
COPY src ./src

# Build the application (skip tests to speed up)
RUN ./mvnw clean package -DskipTests

# ---------- Stage 2: Run the application ----------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=builder /app/target/*.jar app.jar

# Expose Spring Boot's default port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]