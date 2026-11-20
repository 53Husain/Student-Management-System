# Contributing

Thanks for taking an interest in this Student Management System.

## Development setup

1. Install JDK 17 or newer.
2. Install MySQL Server 8 or newer.
3. Clone the repository and open it as a Maven project in IntelliJ IDEA.
4. Run the SQL script at `src/main/resources/schema.sql`.
5. Configure `DB_URL`, `DB_USER` and `DB_PASSWORD` as environment variables.

## Before submitting a change

- Keep Java code compatible with Java 17.
- Use `PreparedStatement` for SQL queries that contain user input.
- Keep database access inside the DAO layer.
- Put business validation in the service layer.
- Add or update tests for changed business logic.
- Run `mvn clean verify` before opening a pull request.

## Pull requests

Describe the problem, summarize the change and include the test result. Never include database passwords or other secrets in commits.
