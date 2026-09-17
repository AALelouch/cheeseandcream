# CheeseAndCream Backend

CheeseAndCream is a Spring Boot REST API for managing agents, categories, productEntities, financial operations, and dashboard metrics for a small business workflow.

It is designed as a portfolio-ready backend project with a clean layered architecture, soft-delete support, financial analytics, and OpenAPI documentation.

## Highlights

- REST API built with Spring Boot and Spring WebMVC
- MySQL persistence with Spring Data JPA
- DTO mapping with MapStruct
- Lombok-based entities and services
- Financial dashboard endpoints for revenue, debt, profit, and pending balance
- Soft delete pattern using `active` flags
- Global exception handling and validation helpers
- CORS configured for a frontend running on `http://localhost:4200`

## Tech Stack

- Java 25
- Spring Boot 4.0.2
- Spring Data JPA
- Spring WebMVC
- MySQL Connector/J
- Lombok
- MapStruct
- SpringDoc OpenAPI
- Docker Compose

## Architecture

The project follows a feature-based hexagonal structure:

```text
infra/{feature}/controller -> application/{feature}/use case + interactor
                                      -> command/query/output ports
                                      <- infra/{feature}/adapters
                                      <- infra/{feature}/persistence
domain/ contains framework-independent business models
```

### Main domain areas

- **Agents**: customers or commercial agents with balances and identity data
- **Products**: catalog items with price, cost, quantity, categoryEntity, and agentEntity ownership
- **Categories**: productEntity classification
- **Identification Types**: identity document types
- **Financial Operations**: sales, purchases, and payments
- **Dashboard**: aggregated metrics for business reporting

## API Overview

Base path: `/api`

### Agents

- `POST /api/agents`
- `GET /api/agents`
- `GET /api/agents/{id}`
- `PUT /api/agents/{id}`
- `DELETE /api/agents/{id}`

### Categories

- `GET /api/categories`
- `GET /api/categories/{id}`
- `POST /api/categories`
- `PUT /api/categories/{id}`
- `DELETE /api/categories/{id}`

### Identification Types

- `GET /api/identity-types`
- `POST /api/identity-types`
- `PUT /api/identity-types/{id}`
- `DELETE /api/identity-types/{id}`

### Products

- `POST /api/productEntities`
- `GET /api/productEntities/{id}`
- `GET /api/productEntities/agentEntity/{agentId}`
- `PUT /api/productEntities/{id}`
- `DELETE /api/productEntities/{id}`

### Financial Operations

- `POST /api/financial-operations`
- `GET /api/financial-operations/agentEntity/{idAgent}`

### Dashboard

- `GET /api/dashboard/monthly/{month}`
- `GET /api/dashboard/pending-balance/total`
- `GET /api/dashboard/pending-balance/agentEntity/{agentId}`
- `GET /api/dashboard/pending-balance/monthly/{month}`

## Financial Metrics

The dashboard exposes the following business metrics:

- **Debt**: total amount owed to suppliers
- **Revenue**: total sales value
- **Profit**: real profit after deducting productEntity cost from sales
- **Pending balance**: accounts receivable stored in the `Agent.balance` field

## Local Development

### Prerequisites

- Java 25
- Maven Wrapper (included)
- Docker Desktop or a local MySQL instance

### Run MySQL with Docker Compose

```bash
docker compose up -d
```

If your environment uses the older command syntax, you can also use:

```bash
docker-compose up -d
```

### Run the application

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

### Build the project

```bash
./mvnw clean package
```

Skip tests if you only need a fast build:

```bash
./mvnw clean package -DskipTests
```

## Configuration

The main configuration lives in `src/main/resources/application.yaml`.

Important defaults:

- App port: `8080`
- Database: `jdbc:mysql://localhost:3306/mydatabase`
- CORS origin: `http://localhost:4200`
- JPA DDL mode: `update`

> If you deploy this project, review the datasource credentials in `application.yaml` and replace local values with secure environment variables.

## Documentation

- `AGENTS.md` — guidance for AI coding agents
- `API_SPECS.md` — detailed API reference
- `API_QUICK_REFERENCE.md` — quick endpoint cheat sheet
- `FRONTEND_COMMUNICATION.md` — concise frontend integration summary
- `FRONTEND_INTEGRATION_GUIDE.md` — code examples for frontend teams
- `PROFIT_CALCULATION_FIX.md` — technical notes on the profit calculation fix

## Project Structure

```text
src/main/java/com/lelouch/cheeseandcream/
├── application/
│   └── {feature}/        # DTOs, use cases, interactors and ports
├── domain/               # business models, validation and exceptions
└── infra/
    ├── {feature}/        # controllers, presenters and adapters
    │   └── persistence/  # JPA entities and repositories
    ├── advice/
    └── config/
```

## Design Notes

- Entities use `active` flags for soft deletes.
- DTOs are implemented with Java records or simple request/response models.
- MapStruct is used to convert between entities and API responses.
- Exceptions are mapped to HTTP responses through `@RestControllerAdvice`.
- JPA lifecycle callbacks are used for creation and update timestamps.

## Swagger / API Explorer

When the application is running, the OpenAPI UI is available at:

```text
http://localhost:8080/swagger-ui.html
```

## Portfolio Notes

This repository is a strong portfolio project because it demonstrates:

- layered backend architecture
- business-rule validation
- REST API design
- financial reporting endpoints
- JPA relationships and repository queries
- MapStruct-based DTO mapping
- global error handling
- real-world business logic such as soft deletes and receivables

## License

No license has been defined yet.
