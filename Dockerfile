# 1️⃣ Build Stage
FROM eclipse-temurin:17-jdk-alpine AS builder

# Install Maven (no need for ./mvnw)
RUN apk add --no-cache maven

WORKDIR /app
COPY . .

# Build the project (skip tests)
RUN mvn clean package -DskipTests

# 2️⃣ Run Stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the built jar from previous stage
COPY --from=builder /app/target/*.jar app.jar

# Expose Spring Boot port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
