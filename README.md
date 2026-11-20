# Student Management System

A menu-driven Java application for managing student records using JDBC and MySQL. Built with Java 17 and Maven.

## Features

- Add, view, update and delete student records
- Search students by ID or name
- Validate names, email addresses and marks
- Calculate grades from marks
- Use JDBC PreparedStatements for database operations

## Tech stack

- Java 17+
- MySQL 8+
- JDBC (MySQL Connector/J)
- Maven
- IntelliJ IDEA

## Project structure

```text
src/main/java/com/husain/studentmanagement/
├── Main.java
├── dao/StudentDAO.java
├── database/DBConnection.java
├── model/Student.java
└── service/StudentService.java
src/main/resources/schema.sql
```

## Prerequisites

1. JDK 17 or newer
2. MySQL Server 8 or newer
3. IntelliJ IDEA
4. Maven (IntelliJ can import the included Maven project)

## Database setup

1. Start your MySQL Server.
2. Open MySQL Workbench and connect to your local server.
3. Open and execute `src/main/resources/schema.sql`.

This creates the `student_management` database and the `students` table.

## Configure database credentials

The application reads these environment variables:

| Variable | Default |
|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3306/student_management?useSSL=false&serverTimezone=UTC` |
| `DB_USER` | `root` |
| `DB_PASSWORD` | Empty string |

Set `DB_USER` and `DB_PASSWORD` to match your local MySQL account. Never commit real credentials.

In IntelliJ IDEA, open **Run > Edit Configurations**, select the Main run configuration, and add the environment variables under **Environment variables**.

## Run the application

1. Open this repository in IntelliJ IDEA.
2. Allow IntelliJ to import the Maven project and download dependencies.
3. Ensure MySQL Server is running and the database schema has been created.
4. Configure the database environment variables.
5. Run `com.husain.studentmanagement.Main`.

Alternatively, run:

```bash
mvn compile exec:java
```

## Grade scale

| Marks | Grade |
|---|---|
| 90–100 | A+ |
| 80–89.99 | A |
| 70–79.99 | B |
| 60–69.99 | C |
| 40–59.99 | D |
| Below 40 | F |

## Security notes

- SQL statements use PreparedStatement parameters.
- Database credentials are read from environment variables.
- Do not commit passwords or local environment files.

## Future improvements

- Add a graphical user interface
- Add unit and integration tests
- Add pagination and sorting
- Add login and role-based access

## Author

Husain Lalji
