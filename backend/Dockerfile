
FROM maven:3.8.5-openjdk-17-slim AS build
LABEL authors="sevastian"
RUN apt-get update && apt-get install -y \
curl

# Set the working directory
WORKDIR /app

# Copy the Maven POM file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Package stage
FROM openjdk:17-jdk-slim
COPY --from=build /app/target/*.war /app/app.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.war"]
