# Personal Finance Dashboard (Backend)

Welcome to the Personal Finance Dashboard backend! This project is designed to help you manage transactions, track your income and expenses, and keep your financial data secure. It uses a Role-Based system to control who can see or change the data.

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
- **Database**: MySQL 8.0
- **Security**: Spring Security + JWT (Tokens)
- **Documentation**: SpringDoc OpenAPI (Swagger)

## How to Setup Locally

### 1. Requirements
- Install **Java 17** or higher.
- Install **Maven**.
- Install **MySQL Server** and create a database named `finance_db`.

### 2. Configuration
Create or update your `src/main/resources/application.properties` with the following configuration:

```bash
server.port=8080

# Database Setup
spring.datasource.url=jdbc:mysql://localhost:3306/finance_db
spring.datasource.username=username
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true 
spring.jpa.properties.hibernate.use_sql_comments=true

# JWT Settings
jwt.secret=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
jwt.expiration=86400000
jwt.refresh-expiration=604800000

# Swagger Documentation
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=alpha
```
**Note:** Just change the `spring.datasource.username` and `spring.datasource.password` to match your local MySQL credentials.

### 3. Build and Run
1. Open your terminal in the project folder.
2. Build the app:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## How to Test APIs (Swagger UI)

I have integrated **Swagger** so you don't need external tools like Postman to test the app.
1. Run the application.
2. Open your browser and go to:
   `http://localhost:8080/swagger-ui.html`

## Important API Paths

| Purpose | Link | Access Level |
| :--- | :--- | :--- |
| **Register/Login** | `/api/auth/**` | Public (Everyone) |
| **Main Summary** | `/api/dashboard/summary` | Admin, Analyst, Viewer |
| **Detailed Trends**| `/api/dashboard/weekly-trends` | Admin, Analyst |
| **Manage Data** | `/api/transactions` | Admin Only |