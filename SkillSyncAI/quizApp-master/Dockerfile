FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
# Use an official OpenJDK runtime as the base image
FROM openjdk:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY --from=build /app/target/quizApp-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on (e.g., 8080)
EXPOSE 8080
ENV gemini_api_key=
ENV spring.datasource.url=ENV spring.datasource.username=
ENV spring.datasource.password=

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
