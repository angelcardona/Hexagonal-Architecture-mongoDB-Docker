 Trading Journal - Trade Logging and Analysis Application
Welcome to the Trading Journal repository! This project serves as a practical and robust example of a backend application built with Spring Boot, where the true star is the Hexagonal Architecture (Ports & Adapters). While the functional application is a trade logging and analysis system, its fundamental design clearly demonstrates how to build highly maintainable, scalable, and technologically flexible software.

The primary goal of this repository is to illustrate the implementation of a clean architecture, where the core business logic is completely isolated from external concerns and specific technologies.

 Key Features (Current Backend)
Trade Registration: Allows saving trading operations with essential details.

Trade Retrieval: Fetch individual trades or a comprehensive list of all operations.

Trade Deletion: Ability to remove trade records.

Data Validation: Ensures data integrity through robust validation mechanisms.

Global Exception Handling: Provides uniform and meaningful error responses to the API client.

MongoDB Persistence: Stores trade data persistently using MongoDB.

Full Dockerization: Packaged and orchestrated with Docker and Docker Compose for a consistent development environment.

Database Admin Interface (Mongo Express): A visual tool for easy interaction with MongoDB.

🏛️ The Project's Core: Hexagonal Architecture (Ports & Adapters)
Hexagonal Architecture is the cornerstone of this project, ensuring extreme decoupling between the central business logic and any external elements. This approach protects your application's domain from contamination by technological details, resulting in a system that is highly adaptable to change and easily testable.

Understanding the Hexagon (The Application Core)
At the center of our architecture resides the Core (The Hexagon). This contains the fundamental business rules and application logic. It communicates with the outside world exclusively through well-defined interfaces, called Ports.

domain Layer (The Hexagon's Center):

Purpose: This is the soul of your application. It encapsulates pure business logic, entities (Trade), value objects, and domain services. It is completely technology-agnostic (it knows nothing about databases, web frameworks, or external systems).

Content: Trade.java (your domain entity), exceptions (custom business exceptions like TradeNotFoundException, InvalidTradeDataException).

Key Principle: Technology independence. Essential business rules must be fully testable without the need for Spring, MongoDB, or a web server.

application Layer (Surrounding the Domain - Part of the Hexagon):

Purpose: Defines the application's use cases and orchestrates domain objects. It exposes the application's capabilities to external actors and establishes contracts for external dependencies.

Content:

ports/input/: Input Ports (Driving Ports). These are interfaces (TradePort) that define what the application can do. They represent the API of your application's core.

ports/output/: Output Ports (Driven Ports). These are interfaces (TradePortOut) that define what the application needs from external systems (e.g., persistence, external APIs).

service/: The concrete implementations of the input ports (TradeService), which contain the use case logic and interact with domain objects and output ports.

Key Principle: Defines the application's API and its external dependencies. It remains technologically agnostic at the interface level.

Interaction with the Outside World (Adapters)
The outer layer of the Hexagonal Architecture consists of Adapters. These are the concrete implementations that connect the Ports to specific technologies.

infrastructure Layer:

Purpose: Houses all the technology-specific code that implements the ports. These are the "plugs" that connect to the hexagon.

Content:

api/ (Primary / Driving Adapter): Implements an Input Port. This is your REST API (TraderRestController), which receives HTTP requests from clients (e.g., a React frontend), translates them into calls to TradePort (the input port), and then maps domain responses back to HTTP DTOs. It also handles API-specific concerns like validation (@Valid) and global exception mapping (GlobalExceptionHandler).

persistence/ (Secondary / Driven Adapter): Implements an Output Port. This is your MongoDB integration (MongoTradeRepositoryAdapter and SpringDataMongoTradeRepository), which translates the needs of TradePortOut (the output port) into specific MongoDB operations.

Key Principle: Adapts the core to specific technologies. Changing a database (e.g., from MongoDB to PostgreSQL) would only require modifying the persistence adapter, not the domain or application layers.

Fundamental Benefits of this Architectural Approach:
Extreme Maintainability: Changes in external technologies (like switching databases or web frameworks) have minimal impact on the core business logic, greatly facilitating system evolution.

Superior Testability: The domain and application layers (the core) can be tested in complete isolation, without the need for a running database or a web server, which speeds up the development cycle and improves quality.

Technological Flexibility: Allows easily swapping adapters (e.g., using a different message queue, a different web framework) without affecting the application's core.

Natural Scalability: Clear delineation of responsibilities and low coupling inherently lead to components that are easier to scale independently.

Future Vision: Preparation for Microservices and Event-Driven Architecture
This modular and decoupled design, inherent in Hexagonal Architecture, serves as the perfect foundation for future evolution towards more distributed architectures like Microservices and Event-Driven Architecture (EDA). Each well-defined hexagonal module within this project could naturally transform into an independent microservice, communicating asynchronously via an event bus to achieve even greater scalability and resilience.

🛠️ Technologies Used
Java 17

Spring Boot 3.x

Spring Web (REST API)

Spring Data MongoDB

Spring Boot Validation (Jakarta Validation)

MongoDB 4.2 (as database engine, compatible with CPUs that lack AVX support)

Docker

Docker Compose

Maven (for dependency management and build automation)

Lombok (to reduce boilerplate code)

MapStruct (for automatic mapping between DTOs and domain entities)

Mongo Express (web-based administration interface for MongoDB in development)

 Getting Started
Follow these steps to get the project up and running on your local machine.

Prerequisites
Ensure you have the following software installed:

Java Development Kit (JDK) 17

Apache Maven (version 3.6.0 or higher)

Docker Desktop (includes Docker Engine and Docker Compose)

1. Clone the Repository
git clone https://github.com/your-username/TradingJournal.git # Replace with your actual repository URL
cd TradingJournal

2. Application Configuration (application.yml)
Create or update the src/main/resources/application.yml file with the following configuration. Note that environment variables set in docker-compose.yml for SPRING_DATA_MONGODB_URI will override these when running with Docker Compose.

# src/main/resources/application.yml
spring:
  data:
    mongodb:
      uri: mongodb://user:1234@mongodb:27017/trading_journal_db?authSource=admin # Ensure credentials match docker-compose.yml
  server:
    port: 8080

3. Dockerfile
Ensure your Dockerfile in the project root has the following content to build an efficient and secure image:

# Dockerfile
# This Dockerfile builds the Docker image for your Spring Boot application.
# It uses a multi-stage build to create a small and efficient final image.

# --- BUILD STAGE ---
FROM maven:3.9.6-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean install -DskipTests

# --- RUN STAGE ---
FROM openjdk:17-jdk-alpine # Using Alpine for a lightweight and secure final image
EXPOSE 8080
WORKDIR /app
COPY --from=build /app/target/project-0.0.1-SNAPSHOT.jar app.jar # Ensure JAR name is correct
ENTRYPOINT ["java", "-jar", "app.jar"]

4. docker-compose.yml
Create or update the docker-compose.yml file in your project root. This file defines and orchestrates your MongoDB, Mongo Express, and Spring Boot application services.

# docker-compose.yml
services:
  mongodb:
    image: mongo:4.2 # MongoDB version compatible with non-AVX CPUs
    container_name: trading-journal-mongodb
    ports:
      - "27017:27017" # MongoDB port accessible from host
    environment:
      MONGO_INITDB_ROOT_USERNAME: user # CHANGE THESE CREDENTIALS!
      MONGO_INITDB_ROOT_PASSWORD: 1234 # CHANGE THESE CREDENTIALS!
    volumes:
      - mongodb_data:/data/db # Volume for persistent DB data
    networks:
      - trading-journal-network
    command: mongod --bind_ip_all --auth # Configure MongoDB to listen on all interfaces and enable authentication

  mongo-express:
    image: mongo-express:latest
    container_name: trading-journal-mongo-express
    restart: always
    ports:
      - "8081:8081" # Mongo Express port accessible from host
    environment:
      ME_CONFIG_MONGODB_SERVER: mongodb # MongoDB service name in Docker Compose
      ME_CONFIG_MONGODB_PORT: 27017
      ME_CONFIG_MONGODB_ADMINUSERNAME: user # Credentials for Mongo Express to connect to MongoDB (must match MongoDB's credentials)
      ME_CONFIG_MONGODB_ADMINPASSWORD: 1234 # ¡CAMBIA ESTAS CREDENCIALES!
      ME_CONFIG_BASICAUTH_USERNAME: admin_me # Credentials for Mongo Express UI login (RECOMMENDED TO BE DIFFERENT!)
      ME_CONFIG_BASICAUTH_PASSWORD: strong_password_me # ¡CAMBIA ESTAS CREDENCIALES por una contraseña FUERTE!
      ME_CONFIG_MONGODB_ENABLE_ADMIN: "true"
    depends_on:
      - mongodb
    networks:
      - trading-journal-network

  app:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: trading-journal-app
    ports:
      - "8080:8080" # Your Spring Boot application port accessible from host
    environment:
      SPRING_DATA_MONGODB_URI: mongodb://user:1234@mongodb:27017/trading_journal_db?authSource=admin # URI for DB connection from your app
    depends_on:
      - mongodb
    networks:
      - trading-journal-network

volumes:
  mongodb_data: # Definition of the named volume for MongoDB data

networks:
  trading-journal-network: # Definition of the custom network for inter-container communication
    driver: bridge

5. Bring Up the Application with Docker Compose
From your terminal, in the project root:

docker-compose up --build -d

up: Starts the services defined in docker-compose.yml.

--build: Forces Docker to rebuild images (necessary for the first time or when you make code/Dockerfile changes).

-d: Runs containers in "detached" mode (in the background).

6. Verify Container Status
docker ps

You should see trading-journal-mongodb, trading-journal-mongo-express, and trading-journal-app running.

7. Access Interfaces
Application API: http://localhost:8080/api/trades

Mongo Express: http://localhost:8081 (use admin_me and strong_password_me for Mongo Express UI login)

🗺️ Project Structure
project/
├── .mvn/                     # Maven Wrapper Scripts
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/bitacora/project/
│   │   │       ├── application/      # Application Layer (Core Business Logic)
│   │   │       │   ├── ports/
│   │   │       │   │   ├── input/    # Input Ports (interfaces for core's capabilities)
│   │   │       │   │   └── output/   # Output Ports (interfaces for core's external dependencies)
│   │   │       │   └── service/      # Use case implementations (TradeService)
│   │   │       ├── domain/           # Domain Layer (Pure Business Entities and Rules)
│   │   │       │   ├── exceptions/   # Custom business exceptions
│   │   │       │   └── Trade.java    # Main domain entity
│   │   │       └── infrastructure/   # Infrastructure Layer (Adapters and External Technologies)
│   │   │           ├── api/          # Primary Adapter (REST API)
│   │   │           │   ├── config/   # API Configurations (GlobalExceptionHandler)
│   │   │           │   ├── dto/      # Request and Response DTOs
│   │   │           │   ├── mapper/   # Mappers (MapStruct)
│   │   │           │   └── TraderRestController.java # REST Controller
│   │   │           └── persistence/  # Secondary Adapter (MongoDB)
│   │   │               ├── adapter/  # Implementation of the persistence output port
│   │   │               ├── document/ # Database document classes
│   │   │               └── SpringDataMongoTradeRepository.java # Spring Data Repository
│   │   └── resources/                # Configuration files (application.yml)
│   └── test/                         # Unit and Integration Tests
├── .gitignore                        # Files and directories to ignore for Git
├── Dockerfile                        # Recipe to build the application's Docker image
├── docker-compose.yml                # Container orchestration (App, MongoDB, Mongo Express)
├── mvnw                               # Maven Wrapper Script (Linux/macOS)
├── mvnw.cmd                           # Maven Wrapper Script (Windows)
└── pom.xml                           # Maven Project Object Model (configuration file)

Note on Package Naming: A common pitfall in Java projects, especially with architectural patterns, is ensuring that the package declarations in your Java files (e.g., package com.bitacora.project.infrastructure.adapters.input.rest;) exactly match their physical directory structure on your file system (src/main/java/com/bitacora/project/infrastructure/...). Mismatches, such as infrastucture instead of infrastructure, can lead to Spring Boot failing to find and inject necessary components (beans). Always use your IDE's refactoring tools to rename packages/directories to ensure consistency.

 Future Enhancements & Roadmap
This project provides a robust foundation. Here are some ideas for future features and expansions:

Interactive Frontend: Build a complete user interface with React to visually interact with the API.

User Authentication & Authorization: Implement Spring Security, JWT, and a multi-user model for individual data ownership.

Advanced Trade Analysis: Incorporate complex metrics (equity curve, R-Multiple, Drawdown, etc.) and visualizations.

Strategy Management: Allow users to define and associate trades with specific strategies.

File Uploads: Enable uploading chart images directly to an object storage service (e.g., S3, Google Cloud Storage).

Notifications: Integrate a notification system (e.g., email alerts for trade milestones).

Transition to Microservices: Modularize the application into smaller, specialized services, communicating via events.

Contributions
Contributions are welcome. If you have ideas or wish to collaborate, feel free to open an issue or submit a pull request.

📄 License
This project is licensed under the MIT License. See the LICENSE file for more details.
