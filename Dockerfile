# Use a base image with Java
FROM openjdk:17-jdk-alpine
# Set the working directory inside the container
WORKDIR /app
# Copy the JAR file into the container
COPY target/your-backend-application.jar app.jar
# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
# Expose the port your Spring Boot app runs on
EXPOSE 8080
