# EvaExchange Trading System

A RESTful API for educational trading operations built with Spring Boot.

## Features

- Buy and sell share operations
- Portfolio management
- Share price tracking
- Transaction history
- Input validation and error handling
- Comprehensive test coverage
- API documentation with Swagger

## Tech Stack

- Java 21
- Spring Boot 3.5
- Spring Data JPA
- PostgreSQL
- Lombok
- JUnit 5
- Mockito
- Swagger/OpenAPI

## Getting Started

### Prerequisites
- Java 21+
- Maven 3.9+
- PostgreSQL 12+

### Setup
1. Clone the repository
2. Create PostgreSQL database: `eva_exchange`
3. Update database credentials in `application.yml`
4. Run: `mvn spring-boot:run`

### API Documentation
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

### Testing
Run tests with: `mvn test`

## API Endpoints

### Buy Shares
```
POST /api/v1/trading/buy
```

### Sell Shares
```
POST /api/v1/trading/sell
```

## Design Principles Applied

- **SOLID Principles**: Clear separation of concerns
- **Clean Architecture**: Layered approach with proper abstractions
- **Domain-Driven Design**: Rich domain models
- **Repository Pattern**: Data access abstraction
- **Service Layer Pattern**: Business logic encapsulation
- **Exception Handling**: Centralized error management
- **Validation**: Input validation at multiple levels
