# Dockerfile
# This Dockerfile builds the Docker image for your Spring Boot application.
# It uses a multi-stage build to create a small and efficient final image.

# --- BUILD STAGE ---
# This stage compiles your Java source code into an executable JAR file.
# We use a Maven image that includes JDK 17 for compilation.
# Using 'maven:3.9.6-openjdk-17-slim' as it's a more recent and reliable tag
# compared to 3.8.7, which was causing "not found" errors.
FROM maven:3-openjdk-17-slim AS build

# Set the working directory inside the container.
# All subsequent commands will be executed from this directory.
WORKDIR /app

# Copy the Maven project configuration file (pom.xml) first.
# This optimizes Docker's build cache. If only source code changes (not dependencies),
# Docker can reuse the cached dependency download layer, speeding up builds.
COPY pom.xml .

# Download all project dependencies.
# '-B' stands for batch mode, which avoids interactive prompts.
# This creates a cached layer of dependencies.
RUN mvn dependency:go-offline -B

# Copy all source code from your local 'src' directory into the container's '/app/src'.
COPY src ./src

# Compile your application and package it into an executable JAR file.
# '-DskipTests' skips running tests during the build, which is common for faster image creation.
# The resulting JAR will typically be in 'target/<your-artifact-id>-<version>.jar'.
RUN mvn clean install -DskipTests

# --- RUN STAGE ---
# This stage creates the final, lightweight Docker image for your application.
# It only includes the Java Runtime Environment (JRE) needed to run your JAR.
# 'openjdk:17-jdk-alpine' is chosen for its minimal size and reduced attack surface,
# making it ideal for production environments.
FROM openjdk:17-jdk-alpine

# Expose the port on which your Spring Boot application will listen.
# This acts as documentation; the actual port mapping to the host is done in docker-compose.yml.
EXPOSE 8080

# Set the working directory for the final application.
WORKDIR /app

# Copy the executable JAR from the 'build' stage to the 'run' stage.
# '--from=build' specifies that the file should be copied from the named 'build' stage.
# 'project-0.0.1-SNAPSHOT.jar' is your application's JAR name (check your pom.xml for exact name).
# It's renamed to 'app.jar' for simplicity within the container.
COPY --from=build /app/target/project-0.0.1-SNAPSHOT.jar app.jar

# Define the command that will be executed when the container starts.
# This command runs your Spring Boot application's JAR file.
ENTRYPOINT ["java", "-jar", "app.jar"]
