# Finance Dashboard (Backend)

This project is designed to help you manage transactions, track your income and expenses, and keep your financial data secure. It uses a Role-Based system to control who can see or change the data.

## Role-Based Access (Who can do what?)

I have created three levels of access to keep the data safe:

- **ADMIN**: The Super User. Can do everything—create, edit, delete transactions, and see all user accounts.
- **ANALYST**: The Specialist. Can view all transaction records and access deep insights like "Recent Activity" and "Weekly Trends." However, they cannot add or delete any records.
- **VIEWER**: The Basic User. Can only see the main Dashboard Summary (Total Income, Expenses, and Balance). They cannot see individual transaction lists or modify any data.

## Features

- **Secure Login**: Protected by JWT (tokens) for safety.
- **Dashboard Summary**: Real-time calculation of your financial health.
- **Soft Delete**: When you delete a transaction, it is hidden from the app but remains safely in the database for history.
- **Automatic Documentation**: Use Swagger to test all APIs directly from your browser.
- **User Status**: Admins can mark accounts as "Active" or "Inactive."

## Tech Stack

- **Java 17** & **Spring Boot 3.3.4**
- **Spring Security** (For Access Control)
- **Oracle SQL** (Database for storage)
- **Hibernate/JPA** (To handle data)
- **Maven** (To manage project tools)

## How to Setup Locally

### 1. Requirements
- Install **Java 17** or higher.
- Install **Maven**.
- Have an **Oracle Database** ready.

### 2. Configuration
Open the file `src/main/resources/application.properties` and update these lines with your database details:
```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
```

### 3. Build and Run
1. Open your terminal in the project folder.
2. Run this command to download everything and build the app:
   ```bash
   mvn clean install
   ```
3. After it finishes, run the main file `DashboardBackendApplication.java` from your IDE or use:
   ```bash
   mvn spring-boot:run
   ```

## How to Test APIs (Swagger UI)

I have integrated **Swagger** so you don't need external tools like Postman to test the app.
1. Run the application.
2. Open your browser and go to:
   `http://localhost:8080/swagger-ui/index.html`

## Important API Paths

| Purpose | Link | Access Level |
| :--- | :--- | :--- |
| **Register/Login** | `/api/auth/**` | Public (Everyone) |
| **Main Summary** | `/api/dashboard/summary` | Admin, Analyst, Viewer |
| **Detailed Trends**| `/api/dashboard/trends` | Admin, Analyst |
| **Manage Data** | `/api/transactions` | Admin Only |