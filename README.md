# OfficeFlow - Office Task & Maintenance Management System

A full-stack, enterprise-grade web application for office task and maintenance management built strictly using **Microsoft SQL Server / SSMS** for the database, **Java 17 & Spring Boot** for the backend, and a modern **Purple & Light White** responsive frontend.

---

## 📁 Repository Structure

```
New2026 project/
├── database/
│   ├── schema.sql           # SSMS T-SQL schema (IDENTITY, NVARCHAR(MAX), BIT, FKs)
│   ├── seed_data.sql        # SSMS initial seed data for all 8 modules
│   └── README.md            # SSMS connection & setup instructions
├── backend/
│   ├── pom.xml              # Maven config (Java 17, Spring Boot 3.2.5, mssql-jdbc)
│   ├── README.md            # IntelliJ IDEA setup & Java/OOP concepts guide
│   └── src/main/
│       ├── java/com/officeflow/
│       │   ├── OfficeFlowApplication.java  # Main entrypoint
│       │   ├── config/                     # CorsConfig, DataInitializer
│       │   ├── controller/                 # 10 REST Controllers (@RestController - direct Entity handling, no DTO)
│       │   ├── entity/                     # 12 JPA Entities with BaseEntity inheritance
│       │   ├── enums/                      # Role, TaskStatus, Priority, EmailType
│       │   ├── exception/                  # ResourceNotFoundException, GlobalExceptionHandler
│       │   ├── repository/                 # 11 Spring Data JPA Repositories
│       │   └── service/                    # Basic Spring Boot Services & Interfaces
│       └── resources/
│           └── application.properties     # SSMS JDBC Connection string & Dialect
└── frontend/
    ├── index.html           # Single Page Application (Purple & Light White theme)
    ├── css/
    │   └── style.css        # Glassmorphism, animations, print preview styles
    └── js/
        ├── api.js           # Dual-mode API adapter (SSMS Backend + offline fallback)
        └── app.js           # Interactive state management, rich editor & excel grids
```

---

## 🗄️ 1. SSMS (Microsoft SQL Server) Connection Setup

> **Note**: This application uses **Microsoft SQL Server / SSMS** exclusively. MySQL is completely excluded.

### Step 1: Open SQL Server Management Studio (SSMS)
1. Launch **SQL Server Management Studio (SSMS)** on your machine.
2. In the **Connect to Server** dialog:
   - **Server type**: `Database Engine`
   - **Server name**: `localhost` (or `localhost\SQLEXPRESS` or `.` or your machine name)
   - **Authentication**: `Windows Authentication` (or `SQL Server Authentication` with login `sa`)
3. Click **Connect**.

### Step 2: Execute Schema & Seed Scripts
1. Click **File -> Open -> File...** (or press `Ctrl+O`).
2. Select [`database/schema.sql`](file:///c:/Users/User/Downloads/project/New2026%20project/database/schema.sql) and click **Execute** (`F5`).
   - This creates database `officeflow_db` and all 11 tables with primary keys, indexes, and foreign keys.
3. Select [`database/seed_data.sql`](file:///c:/Users/User/Downloads/project/New2026%20project/database/seed_data.sql) and click **Execute** (`F5`).
   - This populates demo users, projects, task sheets, notes, evaluations, and email grants.

### Step 3: Verify Connection String
Check [`backend/src/main/resources/application.properties`](file:///c:/Users/User/Downloads/project/New2026%20project/backend/src/main/resources/application.properties):
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=officeflow_db;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=YourStrongPasswordHere
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
```
*(If using Windows Authentication, you can use `integratedSecurity=true` or adjust your SQL Server credentials).*

---

## ☕ 2. Running Backend in IntelliJ IDEA

1. Launch **IntelliJ IDEA**.
2. Click **Open** and select the folder:
   `c:\Users\User\Downloads\project\New2026 project\backend`
3. IntelliJ will detect `pom.xml` as a Maven project and download dependencies (`mssql-jdbc`, `spring-boot-starter-data-jpa`, `spring-boot-starter-web`, `validation`).
4. Ensure Project SDK is set to **Java 17** (`File -> Project Structure -> Project -> SDK: 17`).
5. Open [`com.officeflow.OfficeFlowApplication.java`](file:///c:/Users/User/Downloads/project/New2026%20project/backend/src/main/java/com/officeflow/OfficeFlowApplication.java).
6. Click the green **Run** arrow (or press `Shift + F10`).
7. The server will start on port `http://localhost:8080`.

---

## 🎨 3. Running the Frontend

1. Simply double-click or open [`frontend/index.html`](file:///c:/Users/User/Downloads/project/New2026%20project/frontend/index.html) in Google Chrome, Microsoft Edge, or any modern web browser.
2. The UI includes a **live status badge**:
   - 🟢 **SSMS Backend Connected**: When the Spring Boot server is active on `http://localhost:8080`.
   - 🟣 **Interactive Offline Mode**: If backend is offline, the app runs using browser storage with SSMS schemas, letting you test immediately!

---

## 🌟 4. Core Java & OOP Concepts Implemented

| Concept | Location in Code | Purpose |
| :--- | :--- | :--- |
| **Encapsulation** | All entities in [`com.officeflow.entity`](file:///c:/Users/User/Downloads/project/New2026%20project/backend/src/main/java/com/officeflow/entity) | Private fields with explicit getters and setters protect state. |
| **Inheritance** | [`BaseEntity.java`](file:///c:/Users/User/Downloads/project/New2026%20project/backend/src/main/java/com/officeflow/entity/BaseEntity.java) | Common audit fields (`id`, `createdAt`, `updatedAt`) inherited by entities. |
| **Polymorphism** | [`EvaluationScorer.java`](file:///c:/Users/User/Downloads/project/New2026%20project/backend/src/main/java/com/officeflow/service/EvaluationScorer.java) & [`StandardEvaluationScorer.java`](file:///c:/Users/User/Downloads/project/New2026%20project/backend/src/main/java/com/officeflow/service/impl/StandardEvaluationScorer.java) | Pluggable scoring algorithms for employee evaluation metrics. |
| **Polymorphism** | [`NotificationDispatcher.java`](file:///c:/Users/User/Downloads/project/New2026%20project/backend/src/main/java/com/officeflow/service/NotificationDispatcher.java) & [`EmailNotificationDispatcher.java`](file:///c:/Users/User/Downloads/project/New2026%20project/backend/src/main/java/com/officeflow/service/impl/EmailNotificationDispatcher.java) | Dynamic dispatch strategies for company-wide notifications. |
| **Abstraction** | Service interfaces in [`com.officeflow.service`](file:///c:/Users/User/Downloads/project/New2026%20project/backend/src/main/java/com/officeflow/service) | Decouples business contracts from JPA database implementation. |
| **Functions & Math** | [`StandardEvaluationScorer.calculateOverallScore`](file:///c:/Users/User/Downloads/project/New2026%20project/backend/src/main/java/com/officeflow/service/impl/StandardEvaluationScorer.java#L19-L27) | Intermediate calculations with weighted percentages and rounding. |
| **String Manipulation** | [`AuthServiceImpl.java`](file:///c:/Users/User/Downloads/project/New2026%20project/backend/src/main/java/com/officeflow/service/impl/AuthServiceImpl.java#L30-L37) | Normalization, trimming, casing, and string template formatting. |
| **Arrays & Collections** | [`StreakServiceImpl.java`](file:///c:/Users/User/Downloads/project/New2026%20project/backend/src/main/java/com/officeflow/service/impl/StreakServiceImpl.java#L52-L98) | `TreeSet`, `Arrays.sort()`, array conversion, and streak logic. |

---

## 💼 5. Eight Core Modules Guide

1. **Dashboard Overview**: KPI telemetry metrics (Total Notes, Projects, Streak Days, Reports, Average SLA score, Active Tasks, Email dispatch counts).
2. **Daily Notepad**: Color-coded notes, pinned notes, auto-save, category filtering (Maintenance, Urgent, Checklist, Daily Standup).
3. **Project & Repo Cards**: GitHub links, live demo buttons, copy-to-clipboard repository URL, tech stack tags.
4. **Daily Standup Streak**: Continuous daily update logging, consecutive days counter, milestone badges (Bronze, Silver, Gold, Diamond).
5. **Monthly Report Builder**: Microsoft Word-like rich text editor with toolbar (Bold, Italic, Underline, H1, H2, Bullet List, Tables, Print/PDF export).
6. **Project Idea Comparison**: Side-by-side comparison of **Old Idea & Flaws** vs. **New Idea & Expected Benefits**, impact ratings, and Team Lead approval.
7. **Monthly Performance Evaluation**: Rubric breakdown (SLA 35%, Uptime 25%, Completion 25%, Teamwork 15%) producing composite scores and grades (A+, A, B, C, D).
8. **Excel Sheet Task Distributor**: Team Lead master grid assignment; Team Members update progress, status, and time spent with live completion bars.
9. **Org Email Dispatcher**: Team Lead grants authorization to send from `user@corp.officeflow.com`; team sends downtime alerts and leave requests with audit logging.
