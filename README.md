# Stellar Burgers API Testing
This project focuses on testing the API endpoints of Stellar Burgers.
The goal is to verify the functionality and behavior of the API using automated tests.
The [API documentation](https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf) provides detailed information about the available endpoints.
## General
Technologies used in this project: **Java 11**, **Maven**
## Setup
### To get started with the project, follow these steps:
Clone the repository to your local machine.
Ensure that the following libraries are included and properly configured in your project:
- [**JUnit 4.13.2**](https://mvnrepository.com/artifact/junit/junit/4.13.2)
- [**Rest-assured 5.3.0**](https://mvnrepository.com/artifact/io.rest-assured/rest-assured/5.3.0)
- [**Allure JUnit4 2.21.0**](https://mvnrepository.com/artifact/io.qameta.allure/allure-junit4/2.21.0)
- [**AspectJ Weaver 1.9.7**](https://mvnrepository.com/artifact/org.aspectj/aspectjweaver/1.9.7)
- [**Jackson-databind 2.14.2**](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind/2.14.2)
- [**Apache Commons Lang 3.12.0**](https://mvnrepository.com/artifact/org.apache.commons/commons-lang3/3.12.0)
- [**Java-faker 1.0.2**](https://mvnrepository.com/artifact/com.github.javafaker/javafaker/1.0.2)
## Testing Objectives
### User Creation
- Create a unique user.
- Create a user who is already registered.
- Create a user and leave one of the required fields empty.
### User Login
- Log in with an existing user.
- Log in with an incorrect username and password.
### User Data Modification
- Modify user data with authorization.
- Modify user data without authorization.
### Order Creation
- Create an order with authorization.
- Create an order without authorization.
- Create an order with ingredients.
- Create an order without ingredients.
- Create an order with an incorrect ingredient hash.
### Get Orders for a Specific User
- Get orders for an authorized user.
- Get orders for an unauthorized user.
## Run tests:
```
mvn clean test
```
## Allure Report
### Generate allure-report:
```
mvn allure:report
```
1. Open your project in IntelliJ IDEA.
2. Build your project to generate the Allure report.
3. Once the build is complete, navigate to the `target/site` directory in your project's directory structure.
4. Inside the site directory, you will find the index.html file.
5. Right-click on the index.html file and select "Open in Browser" or "Open in Default Browser" to view the Jacoco report in your preferred web browser.

By opening the index.html file in your browser, you will have access to the detailed test report generated by Allure for your project.

### Run allure-server on localhost:
```
mvn allure:serve 
```
Please note that the exact steps may vary slightly depending on your specific IntelliJ IDEA version and project setup.