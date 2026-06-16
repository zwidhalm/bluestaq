# Notes Vault API

## Overview

A RESTful Notes API built with Spring Boot, PostgreSQL, Docker, and OpenAPI/Swagger.

## Tech Stack

- Java 17
- Spring Boot 3.4.5
- Spring Data JPA
- PostgreSQL
- Docker & Docker Compose
- Swagger/OpenAPI
- JUnit 5
- Mockito

System Overview

The Notes Vault API is a RESTful backend application built using Spring Boot and PostgreSQL. The system follows a layered architecture to separate concerns between HTTP request handling, business logic, and data persistence.

## Quick Start 
Clone the repo 
Cd into "bluestaq" application
Run "docker compose up --build"
Once running, go to "http://localhost:8080/swagger-ui/index.html" in your browser

## 

## Architecture
Client
  ↓
NotesController
  ↓
NotesService
  ↓
NotesServiceImpl
  ↓
NotesRepository
  ↓
PostgreSQL

## 

## Request Flow
-A client sends an HTTP request to the API.
-The controller validates and receives the request.
-The service layer applies business logic.
-The repository layer interacts with the database using Spring Data JPA.
-The response is mapped to a DTO and returned to the client as JSON.

## 

## Key Components:

Controller Layer

Handles HTTP requests and responses.

GET /notes
GET /notes/{id}
POST /notes
DELETE /notes/{id}

Service Layer

Contains application business logic and orchestrates interactions between the controller and repository layers.

Repository Layer

Uses Spring Data JPA to perform database operations on Note entities.

Database

PostgreSQL stores note records containing:

-id
-content
-createdAt

## 

## Deployment

The application is containerized using Docker and can be started with a single command:

docker compose up --build

Docker Compose provisions both:

Spring Boot API
PostgreSQL database
Testing

## 

The project includes:

API layer testing using MockMvc
Service layer testing using JUnit and Mockito

## API Usage Examples

### Create a Note

**Request**

```http
POST /notes
Content-Type: application/json
```

```json
{
  "content": "Buy milk"
}
```

**Response**

```json
{
  "id": 1,
  "content": "Buy milk",
  "createdAt": "2026-06-16T03:15:00Z"
}
```

---

### Get All Notes

**Request**

```http
GET /notes
```

**Response**

```json
[
  {
    "id": 1,
    "content": "Buy milk",
    "createdAt": "2026-06-16T03:15:00Z"
  },
  {
    "id": 2,
    "content": "Schedule dentist appointment",
    "createdAt": "2026-06-16T03:20:00Z"
  }
]
```

---

### Get Note by ID

**Request**

```http
GET /notes/1
```

**Response**

```json
{
  "id": 1,
  "content": "Buy milk",
  "createdAt": "2026-06-16T03:15:00Z"
}
```

---

### Delete Note

**Request**

```http
DELETE /notes/1
```

**Response**

```json
{
  "id": 1,
  "content": "Buy milk",
  "createdAt": "2026-06-16T03:15:00Z"
}
```

---

### Swagger UI

Once the application is running:

```text
http://localhost:8080/swagger-ui/index.html
```

Swagger can be used to explore and execute all API operations interactively.


## Assumptions

The following assumptions were made during development:

* Notes are simple text records and do not require user authentication or authorization.
* Each note consists of an identifier, content, and creation timestamp.
* The API is intended for a single-tenant environment and does not support multiple users.
* PostgreSQL is available through Docker Compose and is the primary persistence mechanism.
* API consumers interact with the service using JSON over HTTP.

## Design Decisions & Trade-offs

### Layered Architecture

The application follows a Controller → Service → Repository architecture to maintain separation of concerns and improve maintainability.

**Trade-off:** Additional abstraction introduces more classes than a minimal CRUD implementation but improves scalability and testability. One could argue that this approach is "over-engineered" but I did this to reflect the real world codebases that I have worked on.

### DTO-Based API Contract

Request and response DTOs are used instead of exposing JPA entities directly.

**Trade-off:** Additional mapping code is required, but the API contract remains independent of persistence-layer changes.

### Spring Data JPA

Spring Data JPA was selected to reduce boilerplate and accelerate development.

**Trade-off:** While convenient for CRUD operations, complex queries may require custom repository implementations or native SQL in larger systems.

### Docker Compose Deployment

Docker Compose was used to provide a reproducible local development environment.

**Trade-off:** Docker introduces an additional dependency but eliminates manual database setup and environment inconsistencies.

### Testing Strategy

The project includes:

- Controller/API testing using MockMvc
- Service-layer testing using JUnit and Mockito

**Trade-off:** The current tests focus on application behavior and business logic rather than full end-to-end integration testing.

## Future Improvements

Potential enhancements include:

- database migrations for schema versioning
- Update note endpoint (`PUT /notes/{id}`)
- Pagination and sorting support for large note collections i.e (`GET /notes`)
- Search functionality by content
- Improved validation and standardized error responses (removing try/catch and letting Spring handle granular exceptions with @ControllerAdvice)
- Additional integration and repository tests
- Authentication and authorization using Spring Security and JWT
- Metrics and health monitoring via Spring Boot Actuator
- CI/CD pipeline integration for automated testing and deployment
