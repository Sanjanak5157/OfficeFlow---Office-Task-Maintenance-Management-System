# OfficeFlow - Spring Boot Backend Guide (SSMS / Microsoft SQL Server)

This backend is built with **Java 17 (LTS)**, **Spring Boot 3.2.x**, and **Spring Data JPA** connecting directly to **Microsoft SQL Server / SSMS (SQL Server Management Studio)**.

---

## 1. What Each Package is Used For (Package Architecture)

| Package | Purpose & Explanation |
| :--- | :--- |
| `com.officeflow` | Contains `OfficeFlowApplication.java`, the main entry point to start the Spring Boot embedded container. |
| `com.officeflow.config` | Holds configuration classes: `CorsConfig.java` for browser cross-origin requests, and `DataInitializer.java` to auto-populate SSMS records if tables are empty. |
| `com.officeflow.controller` | The REST API layer (`@RestController`). Direct CRUD handling with `@RequestBody Entity entity` - clean & basic without DTO boilerplate. |
| `com.officeflow.entity` | JPA Database Models mapping directly to SQL Server tables (`@Entity`, `@Table`). Represents domain entities, uses `NVARCHAR(MAX)` for rich text, and demonstrates **OOP Inheritance** via `BaseEntity`. |
| `com.officeflow.enums` | Type-safe Java Enumerations (`Role`, `TaskStatus`, `Priority`, `EmailType`). |
| `com.officeflow.exception` | Custom exceptions (`ResourceNotFoundException`) and `@RestControllerAdvice` (`GlobalExceptionHandler`) to format errors into friendly JSON. |
| `com.officeflow.repository` | Spring Data JPA interfaces extending `JpaRepository<T, ID>` for automatic SQL generation and custom JPQL queries. |
| `com.officeflow.service` | Service interfaces defining the business contracts (demonstrating **OOP Abstraction**). |
| `com.officeflow.service.impl` | Service implementation classes containing core algorithms, calculations, business workflows, and repository calls. |

---

## 2. Core Java Concepts & OOP Used in This Project

### A. Basic Java Concepts
1. **Functions / Methods & Overloading**:
   - `calculateOverallScoreAndGrade()` in `PerformanceEvaluation.java`
   - Method Overloading in `NoteServiceImpl.java`: `formatNoteContent(String)` and `formatNoteContent(String, String[])`
2. **String Handling**:
   - `StringBuilder` in `ReportServiceImpl.java` for compiling rich HTML document reports.
   - String manipulation methods: `.trim()`, `.toLowerCase()`, `.split()`, `.contains()`, `.equalsIgnoreCase()` across validation and search routines.
3. **Arrays & Collections**:
   - Fixed Arrays: `STREAK_MILESTONES = {3, 7, 14, 21, 30, 60, 100}` and `VALID_CATEGORIES = {...}`
   - Array traversal via `for` and `for-each` loops
   - Dynamic Collections: `List<TaskRowItem>`, `Set<LocalDate>`, `Map<String, Object>`

### B. Java OOP (Object-Oriented Programming)
1. **Encapsulation**:
   - All entity fields are `private` or `protected`. Data is read and modified strictly via getter and setter methods.
2. **Inheritance**:
   - `BaseEntity.java` provides common attributes (`id`, `createdAt`, `updatedAt`, `@PrePersist`, `@PreUpdate`).
   - Every domain entity (`User`, `Note`, `ProjectRepo`, `StreakLog`, `MonthlyReport`, etc.) `extends BaseEntity`.
3. **Polymorphism**:
   - Strategy Pattern: `EvaluationScorer` interface with `StandardEvaluationScorer` implementation.
   - Notification Dispatcher: `NotificationDispatcher` interface with `EmailNotificationDispatcher` implementation.
4. **Abstraction**:
   - Interfaces separate API contracts from implementation classes (`NoteService` -> `NoteServiceImpl`, `TaskSheetService` -> `TaskSheetServiceImpl`).

---

## 3. How to Connect to SSMS & Run in IntelliJ IDEA

1. **Set Up Database in SSMS**:
   - Open **SQL Server Management Studio (SSMS)**.
   - Connect to your SQL Server instance (e.g. `localhost` or `localhost\SQLEXPRESS`).
   - Open and execute `database/schema.sql` (press `F5`).
   - Open and execute `database/seed_data.sql` (press `F5`).
2. **Open Project in IntelliJ IDEA**:
   - Open IntelliJ IDEA -> **File** -> **Open...** -> select `backend/pom.xml`.
   - Ensure Project SDK is set to **Java 17** (or 21 LTS).
3. **Configure SQL Server Credentials**:
   - Open `src/main/resources/application.properties`.
   - Update `spring.datasource.username` and `spring.datasource.password` to match your SQL Server user (e.g., `sa` / your password).
4. **Run Application**:
   - Open `src/main/java/com/officeflow/OfficeFlowApplication.java`.
   - Click the green **Run ▶** button.
   - The application connects to SSMS / SQL Server and launches at `http://localhost:8080`.
