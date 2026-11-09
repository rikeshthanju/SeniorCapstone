# Start 1 Spark Inventory Management System

A Java Spring Boot inventory management website using PostgreSQL.

## Requirements
- Java 21
- PostgreSQL
- pgAdmin 4
- Git

## Setup
1. Clone the GitHub repository.
2. Create a PostgreSQL database named `start1spark_inventory`.
3. Run `database/schema.sql` against the new database in pgAdmin.
4. Optionally run `database/sample-data.sql` once to insert fictional sample records.
5. Open the project folder in VS Code.
6. Set the `DB_PASSWORD` environment variable to your local PostgreSQL password. Configure `DB_URL` and `DB_USERNAME` too if required by application.properties.
7. Run `.\gradlew.bat bootRun` in PowerShell.
8. Visit http://localhost:8080.

## Current Features
- Homepage and navigation
- PostgreSQL-backed inventory list
- Add inventory item form
- Inventory fields modeled after the sponsor's spreadsheet

## Important
This is a development prototype, not a production system. Do not commit real donor information, passwords, or private sponsor data. Cloud deployment, authentication, and final security controls are pending.
