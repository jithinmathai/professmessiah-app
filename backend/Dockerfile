# Use a lightweight JRE image for Java 21
FROM eclipse-temurin:21-jre-alpine

# Set the working directory
WORKDIR /app

# Copy the pre-built JAR file from the target directory to the container
COPY target/backend-1.0-SNAPSHOT.jar /app/app.jar

# Expose port 8080 for the application
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
