# Mercado Free

A web application for displaying and searching products, built with Spring Boot (backend) and Angular (frontend).

## Project Overview

Mercado Free is a simple e-commerce platform that allows users to:
- Browse a catalog of products
- View detailed product information
- Search for products by name, description, or brand

## Architecture

The application follows a microservices architecture with:

- **Backend API**: Spring Boot application that manages product data
- **Frontend UI**: Angular application that provides the user interface

## Technologies Used

### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database (for development)
- Maven

### Frontend
- Angular
- TypeScript
- HTML/CSS

### DevOps
- Docker
- GitLab CI/CD
- Maven

## API Endpoints

The backend provides the following REST endpoints:

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/product` | GET | Get all products |
| `/product/{id}` | GET | Get a specific product by ID |
| `/search/{searchField}` | GET | Search products by name, description, or brand |

## Setup and Installation

### Prerequisites
- Docker and Docker Compose
- Git

### Steps to Run

1. Clone the repository:
   ```
   git clone <repository-url>
   cd mercado-free
   ```

2. Start the application using Docker Compose:
   ```
   docker-compose up
   ```

3. Access the application:
    - Frontend: http://localhost:4200
    - Backend API: http://localhost:8080

## Development

### Backend Development

The backend is a Spring Boot application located in the `api` directory.

To run the backend locally:

```
cd api
mvn spring-boot:run
```

### Frontend Development

The frontend is an Angular application located in the `ui` directory.

To run the frontend locally:

```
cd ui
npm install
ng serve
```

## CI/CD Pipeline

The project includes a GitLab CI/CD pipeline with the following stages:

1. **Documentation**: Generates JavaDoc for the API
2. **Package**: Builds the Java application
3. **Build App (Dev)**: Builds and pushes Docker images for both backend and frontend

## Project Structure

```
mercado-free/
├── api/                  # Backend Spring Boot application
│   ├── src/              # Source code
│   ├── Dockerfile        # Docker configuration for backend
│   └── pom.xml           # Maven configuration
├── ui/                   # Frontend Angular application
│   ├── src/              # Source code
│   └── Dockerfile        # Docker configuration for frontend
├── docker-compose.yml    # Docker Compose configuration
└── .gitlab-ci.yml        # CI/CD configuration
```
