# REST API Test Automation Framework

This project is a reusable API Test automation framework built using Java, Gradle, RestAssured, and BDD Cucumber.

## API Under Test

The framework tests the [restful-api.dev](https://restful-api.dev/) open-source API, which allows users to create, list, update, and delete objects.

### Endpoints Covered:
- **Add an object:** `POST /objects`
- **Get an object:** `GET /objects/{id}`
- **List all objects:** `GET /objects`
- **Delete an object:** `DELETE /objects/{id}`

## Framework Features

- **ApiClient:** Abstracted API interactions into a dedicated `ApiClient` class, separating business logic from step definitions and making the API client reusable in other contexts.
- **BDD with Cucumber:** Scenarios are written in plain English using Gherkin syntax.
- **RestAssured:** Used for making HTTP requests and performing assertions.
- **Data Sharing:** Demonstrates sharing information (like item IDs) between different steps and REST calls using a `TestContext` class.
- **POJO Modeling:** Uses Java objects (with Lombok) for request/response bodies, promoting clean code and type safety.
- **Error & Edge Case Handling:** Includes scenarios for non-existent items and verification after deletion.
- **JSON Path Assertions:** Utilizes RestAssured's built-in JSON Path support for robust assertions.
- **Schema Validation:** Uses `rest-assured-json-schema-validator` to validate that the API response matches a predefined JSON schema file (`device_schema.json`).

## Project Structure

- `src/test/java/com/example/api/client`: `ApiClient` for reusable API interaction logic.
- `src/test/java/com/example/api/model`: POJO classes for API data.
- `src/test/java/com/example/api/config`: Configuration constants (Base URL, Endpoints).
- `src/test/java/com/example/api/utils`: Utility classes like `TestContext` for sharing state.
- `src/test/java/com/example/api/stepdefs`: Cucumber step definitions implementation.
- `src/test/resources/features`: BDD feature files (`create_items.feature`, `get_items.feature`, `delete_items.feature`).
- `src/test/resources/schemas`: JSON schema files for response validation.
- `RunCucumberTest.java`: JUnit 5 Platform Suite runner for Cucumber.

## Reusability Summary

This framework is highly reusable because:
1. **Decoupled API Client:** The `ApiClient` class can be easily updated or extended for other APIs without changing the test logic.
2. **Generic State Management:** `TestContext` provides a flexible way to pass data between steps without tight coupling.
3. **Modular Steps:** Step definitions are designed to be generic (e.g., using placeholders for data types).
4. **Configurable:** All environment-specific details are centralized in `Config.java`.
5. **POJO-based Data Handling:** Using POJOs with Jackson/Lombok makes it easy to switch between different data schemas.

## How to Run

### Prerequisites
- Java 17 or higher
- Internet connection (to access the API)

### Running Tests via Gradle
To run all tests:
```bash
./gradlew test
```

### Reports
After running the tests, an HTML report is generated at:
`target/cucumber-reports.html`

## Sample Scenarios

The framework includes the following scenarios:
1. **Verify an item can be created:** Tests `POST /objects` and verifies the response.
2. **Ability to return an item:** Creates an item and then retrieves it using `GET /objects/{id}`.
3. **Ability to list multiple items:** Tests `GET /objects`.
4. **Ability to delete an item:** Creates an item, deletes it, and verifies it's gone.
5. **Error case - Get non-existent item:** Verifies `404 Not Found` for an invalid ID.
