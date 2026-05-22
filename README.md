# Diogenes

Diogenes is a Java web application built with Spring Boot. 

## Current Features

- Journal with automatic stress categorization
- Quick wellness check-in with Java-based age-aware recommendation logic
- Structured surveys page (PHQ-9, GAD-7, WHO-5) with scored results and guidance
- Dated journal entries
- Support guidance for different age groups
- Input validation

## Tech Stack

- Java 21
- Spring Boot
- Spring MVC
- Thymeleaf
- Spring Data JPA
- H2 Database
- HTML/CSS

## Project Structure

src/main/java/com/diogenes
|- controller
|- model
|- repository
|- service

## How to Run

1. Open the project in the terminal.
2. Run(in bash):
mvn spring-boot:run


3. Open `http://localhost:8080`
4. Open H2 console at `http://localhost:8080/h2-console` for data base

## Pages

### Home

Shows the main navigation and links to the main sections.

### Assessment

Stores the latest quick check-in based on age group, sleep, energy, anxiety, and support choices.

### Surveys

Separate page at `/survey` for PHQ-9 (depression), GAD-7 (anxiety), and WHO-5 (well-being). Each submission saves total score, severity label, recommendation, and urgent-support flag when needed.

### Journal

Saves journal entries with a date, mood, reflection, and calculated stress category.

### Community and Support

Support now includes grounded guidance cards. Community is only a dummy placeholder page.
