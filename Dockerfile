FROM eclipse-temurin:21-jdk-alpine AS base
WORKDIR /app

# Install Maven
RUN apk add --no-cache maven

# Copy pom.xml first for dependency caching
COPY pom.xml ./

# Download dependencies for caching
RUN mvn dependency:go-offline -B


FROM base AS dev

# Expose application and debug ports
EXPOSE 8080 5005

# Run with DevTools and Remote Debug enabled
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"]


FROM base AS build

# Copy source code
COPY src ./src

# Build the application (skip tests for faster build)
RUN mvn package -DskipTests


FROM eclipse-temurin:21-jre-alpine AS prod
WORKDIR /app

# Create non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copy only the JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
