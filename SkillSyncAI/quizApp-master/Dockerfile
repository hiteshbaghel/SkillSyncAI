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
ENV gemini_api_key=AIzaSyB8PLx-3dgX2q8BfRCi5leblqmBjCFa0HI
ENV spring.datasource.url=jdbc:mysql://bytewise24.cli6o6w62u7f.ap-south-1.rds.amazonaws.com/bytewise_db?useSSL=true&allowPublicKeyRetrieval=true&connectTimeout=30000&socketTimeout=30000
ENV spring.datasource.username=admin24
ENV spring.datasource.password=bytewise24

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
