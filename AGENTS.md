# AGENTS.md - CheeseAndCream Backend Guide

**Project**: Spring Boot REST API for productEntity/agentEntity/financial operations management  
**Stack**: Java 25, Spring Boot 4.0.2, MySQL 8.0, MapStruct, Lombok  
**Architecture**: Clean Layers Pattern (Controller → Service → Repository → Entity)

---

## Project Architecture Overview

### Core Layer Structure
```
controller/ → service/ (interface) → service/impl/ (implementation)
    ↓                                      ↓
 Models (Request/Response Records)    Mappers (MapStruct)
    ↑                                      ↓
    ←--------- entity/ ←---- repository/ ←
```

### Key Design Decisions
- **Soft Deletes**: All entities use `active: boolean` flag instead of hard deletion (see `Product.java` lines 41, 76-79)
- **DTOs as Records**: Request/Response use Java Records (immutable, modern approach)
- **Entity Lifecycle**: Uses `@PrePersist/@PreUpdate` for timestamp management (`Product.java` lines 56-65)
- **Optimistic Locking**: Entities include `@Version` for concurrent access safety
- **Lazy Loading**: All relationships use `FetchType.LAZY` to prevent N+1 queries

---

## Module-Specific Patterns

### Adding a New CRUD Module (e.g., Category)
1. **Create Entity**: `src/main/java/com/lelouch/cheeseandcream/entity/productEntity/Category.java`
   - Must include: `@Id`, `@Version`, `active` boolean, timestamps with `@PrePersist/@PreUpdate`

2. **Create DTOs**: In `src/main/java/com/lelouch/cheeseandcream/model/`
   ```java
   public record CategoryRequest(String name, String description) {}
   public record CategoryResponse(Long id, String name, String description) {}
   ```

3. **Create Mapper**: `src/main/java/com/lelouch/cheeseandcream/mapper/CategoryMapper.java`
   ```java
   @Mapper(componentModel = "spring")
   public interface CategoryMapper {
       CategoryResponse toResponse(Category categoryEntity);
       Category toEntity(CategoryRequest request);
   }
   ```

4. **Create Service Interface**: `src/main/java/com/lelouch/cheeseandcream/service/CategoryCrudService.java`

5. **Create Implementation**: `src/main/java/com/lelouch/cheeseandcream/service/impl/CategoryCrudServiceImpl.java`
   - Always inject dependencies via constructor (no `@Autowired`)
   - Use `ValidatorUtils.validateData()` for business rule validation

6. **Create Controller**: `src/main/java/com/lelouch/cheeseandcream/controller/CategoryRestController.java`
   - Map to `/api/categories` following REST standards
   - Inject service via constructor

### Dashboard Service Pattern (Analytics/Aggregations)
**File**: `service/impl/DashBoardServiceImpl.java`

For services that aggregate data across multiple records:

1. **Add Query Methods to Repository** (using `@Query`)
   ```java
   @Query("SELECT SUM(t.total) FROM FinancialOperationEntity t WHERE t.active = true 
           AND t.creationDate BETWEEN :startDate AND :endDate 
           AND t.operationType = 'SALE'")
   Double sumRevenueByTimeRange(@Param("startDate") LocalDateTime startDate, 
                                @Param("endDate") LocalDateTime endDate);
   ```

2. **Implement Service Methods**
   - Calculate date ranges in the service (e.g., `LocalDateTime.of(year, month, 1, 0, 0, 0)`)
   - Call repository query methods
   - **Always handle null results** → return 0.0 if no data found
   ```java
   Double revenue = financialOperationRepository.sumRevenueByTimeRange(startDate, endDate);
   return revenue != null ? revenue : 0.0;
   ```

3. **Key Financial Metrics** (FinancialOperation aggregations)
   - **Debt**: Sum of PURCHASE operations (`operationType = 'PURCHASE'`)
   - **Revenue**: Sum of all SALE operations (`operationType = 'SALE'`)
   - **Profit** (Actual): SUM(OperationProduct.totalPrice - Product.cost * OperationProduct.quantity)
     - Correctly deducts productEntity costs from sales revenue
     - Formula: For each sold productEntity: (sales_total - cost_per_unit * quantity_sold)
     ```java
     @Query("SELECT COALESCE(SUM(op.totalPrice - (p.cost * op.quantity)), 0) " +
            "FROM FinancialOperationEntity f " +
            "JOIN f.productEntities op " +
            "JOIN op.productEntity p " +
            "WHERE f.active = true AND f.operationType = 'SALE' " +
            "AND f.creationDate BETWEEN :startDate AND :endDate")
     Double sumProfitByTimeRange(@Param("startDate") LocalDateTime startDate, 
                                 @Param("endDate") LocalDateTime endDate);
     ```
   - **Accounts Receivable (Pending Balance)**: `SUM(SALE) - SUM(PAYMENT)` = Amount customers still owe
   - **Usage Cases**:
     - `getTotalPendingBalance()`: Total accounts receivable (overall)
     - `getPendingBalanceByMonth(int month)`: Pending balance for a specific month
     - `getPendingBalanceByAgent(Long agentId)`: How much a specific customer owes
   - **SQL Technique**: Uses `CASE WHEN` to treat PAYMENT as negative and `COALESCE` to handle nulls

### Validation & Error Handling Patterns
- **Validation Utility**: `ValidatorUtils.validateData(BooleanSupplier, String)` 
  - Throws `BadRequestException` if condition evaluates to true
  - Example: `ValidatorUtils.validateData(() -> productRepository.existsByName(...), "Product already exists")`

- **Exception Types**:
  - `NotFoundException` → Handled by `NotFoundExceptionAdvice` → HTTP 404
  - `BadRequestException` → Handled by `BadRequestExceptionAdvice` → HTTP 400

- **Repository Custom Queries**: Use method names for queries
  ```java
  // Repository queries follow Spring Data conventions
  findByIdAndActiveIsTrue(Long id)  // Soft delete safety
  existsByNameAndActiveIsTrueAndAgentId(String name, Long agentId)
  findByAgentIdAndActiveIsTrue(Long agentId)
  ```

---

## Critical Conventions

### Naming Rules
- **Controllers**: `*RestController` (e.g., `ProductRestController`)
- **Services**: `*CrudService` interface + `*CrudServiceImpl` implementation
- **Mappers**: `*Mapper` interface (MapStruct generates `*MapperImpl`)
- **Endpoints**: `/api/{resource}` (e.g., `/api/productEntities`, `/api/agents`)

### Entity Relationships
- **All relationships use `cascade = CascadeType.DETACH`** (prevents accidental cascades)
- **Fetch strategy is LAZY** to avoid eager loading performance issues
- **Example** (`Product.java` lines 43-50):
  ```java
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agentEntity;
  ```

### Lombok Usage
- Use `@Data` for entities (generates getters/setters/equals/hashCode/toString)
- Always pair with `@NoArgsConstructor` and `@AllArgsConstructor` for JPA compatibility

### CORS Configuration
- Configured via `WebConfig.java` with allowed origins from `application.yaml`
- Current: `http://localhost:4200` (Angular frontend)
- Allowed methods: GET, POST, PUT, DELETE, OPTIONS

---

## Build & Execution

### Prerequisites
- **Java**: Version 25 (configured in `pom.xml`)
- **Maven**: Version 3.6+
- **Docker**: For MySQL via `compose.yaml`

### Build Commands
```bash
# Clean build
./mvnw clean package

# Run locally (requires MySQL running)
./mvnw spring-boot:run

# Quick rebuild (skip tests)
./mvnw clean package -DskipTests
```

### Database Setup
```bash
# Start MySQL via Docker Compose
docker-compose up -d

# Database: mydatabase
# UserEntity: root / Password: verysecret
# Port: 3306
```

### Development Workflow
1. **HibernateHint**: `spring.jpa.hibernate.ddl-auto=update` auto-creates tables
2. **SQL Logging**: Enabled in `application.yaml` (`show-sql: true`, `format_sql: true`)
3. **Hot Reload**: `spring-boot-devtools` included for instant restarts on code changes

---

## MapStruct Mapper Patterns

### Complex Mappings with Relationships
```java
@Mapper(componentModel = "spring")
public interface ProductMapper {
    // Maps nested entity properties to DTO fields
    @Mapping(target = "categoryName", source = "categoryEntity.name")
    @Mapping(target = "agentName", source = "agentEntity.name")
    ProductResponse toResponse(Product productEntity);
    
    Product toEntity(ProductRequest productRequest);
}
```
- **Source**: Entity with relationships
- **Target**: Response DTO with flattened strings
- **Annotation Processing**: Configured in `pom.xml` with `mapstruct-processor` + `lombok-mapstruct-binding`

---

## Key Files for Reference

| File | Purpose |
|------|---------|
| `pom.xml` | Dependencies, build config, Java 25 target |
| `application.yaml` | MySQL connection, JPA config, CORS origins |
| `compose.yaml` | MySQL Docker service |
| `ValidatorUtils.java` | Business validation helper |
| `exception/{*Exception,advice/*Advice}.java` | Custom exceptions + global handlers |
| `config/WebConfig.java` | CORS configuration |
| `src/main/java/com/lelouch/cheeseandcream/service/impl/ProductCrudServiceImpl.java` | Template for new service implementations |

---

## Testing Guidance

- Test repositories with custom queries (e.g., `findByIdAndActiveIsTrue`)
- Mock mappers for service layer tests
- Test validation logic with `ValidatorUtils.validateData`
- Use Spring Boot Test annotations: `@DataJpaTest`, `@WebMvcTest`, `@SpringBootTest`

---

## Common Pitfalls to Avoid

1. **Forgetting `activeIsTrue` in queries** → Returns soft-deleted entities
2. **Using `@Autowired`** → Use constructor injection (already established pattern)
3. **Cascade propagation** → All relationships use `DETACH` to prevent accidental updates
4. **Missing timestamp management** → New entities must include `@PrePersist/@PreUpdate` callbacks
5. **Hard mapping in mappers** → Use `@Mapping` annotations for complex fields

---

## External Integrations

- **SpringDoc OpenAPI**: Swagger UI at `http://localhost:8080/swagger-ui.html`
- **MySQL**: Via Docker Compose or local installation
- **Frontend**: Expected at `http://localhost:4200` (Angular)


