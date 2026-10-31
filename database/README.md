# SSMS (SQL Server Management Studio) Database Setup Guide

This project connects to **Microsoft SQL Server** via **SSMS (SQL Server Management Studio)**.

---

## 1. How to Run Scripts in SSMS (SQL Server Management Studio)

### Step 1: Open SSMS and Connect
1. Launch **SQL Server Management Studio (SSMS)** on your PC.
2. In the "Connect to Server" dialog:
   - **Server type**: Database Engine
   - **Server name**: `localhost` or `.` or `localhost\SQLEXPRESS` (depending on your installation)
   - **Authentication**: `Windows Authentication` or `SQL Server Authentication` (`sa` / your password)
3. Click **Connect**.

### Step 2: Execute `schema.sql`
1. In SSMS, click **File** -> **Open** -> **File...** (or press `Ctrl + O`).
2. Select `database/schema.sql`.
3. Click the green **Execute** button (or press `F5`).
4. The database `officeflow_db` and all 11 tables will be created.

### Step 3: Execute `seed_data.sql`
1. In SSMS, open `database/seed_data.sql`.
2. Ensure the dropdown at top left displays `officeflow_db`.
3. Click **Execute** (or press `F5`).
4. Default users, project cards, daily streaks, reports, and spreadsheets will be populated!

---

## 2. Spring Boot Connection Configuration

In `backend/src/main/resources/application.properties`, configure your SQL Server details:

### Option A: SQL Server Authentication (Recommended)
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=officeflow_db;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=YOUR_SQL_PASSWORD
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
```

### Option B: Named Instance (e.g. SQLEXPRESS)
If you are using SQL Express:
```properties
spring.datasource.url=jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=officeflow_db;encrypt=true;trustServerCertificate=true
```

### Option C: Windows Authentication (Integrated Security)
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=officeflow_db;integratedSecurity=true;encrypt=true;trustServerCertificate=true
```

---

## 3. Default Demo Accounts
- **Team Lead**:
  - Email: `lead@officeflow.com`
  - Password: `admin123`
  - Name: Alex Rivera
- **Senior Systems Technician**:
  - Email: `sarah@officeflow.com`
  - Password: `user123`
  - Name: Sarah Connor
- **Network Maintenance Engineer**:
  - Email: `david@officeflow.com`
  - Password: `user123`
  - Name: David Chen
