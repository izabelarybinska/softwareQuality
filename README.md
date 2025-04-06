# Jabberpoint

Jabberpoint is a slide show presentation tool developed for the master course Design for Change at the Open University. The project is meant to demonstrate the process of creating software in a future-proof way, by using an agreed upon ubiquitous language, applying common Object-Oriented design patterns, and following several design principles.
@author Janko-dev

This repository is an already refactored version of the project and has completed the process.

---

## Contents

1. [Introduction](#introduction)
2. [What is DTAP?](#what-is-dtap)
3. [DTAP Workflow in Jabberpoint](#dtap-workflow-in-jabberpoint)
4. [Installation](#installation)

---

## Introduction

Jabberpoint is a Java-based application designed to create and display dynamic presentations. The project leverages various design patterns, such as **Command**, **Bridge** and **Abstract Factory**, to ensure maintainability and scalability. To maintain quality software, Jabberpoint integrates a structured DTAP (Development, Test, Acceptance, Production) workflow, alongside automated workflows tailored for each phase of the cycle.

---

## What is DTAP?

DTAP is a development methodology that ensures the software moves through defined stages to enhance quality and reliability. Here's what each phase entails:

### Development
The first stage where features are implemented, and code is analyzed for compliance with coding standards. Static code analysis and linting tools are used to detect potential issues early.

### Test
Comprehensive testing is carried out in this phase. Code coverage analysis ensures that all critical parts of the application are tested. Security checks are also performed to identify vulnerabilities.

### Acceptance
This phase focuses on ensuring the software meets the functional and non-functional requirements of the end-users. Builds are generated, dependencies are managed via Maven, and the application undergoes thorough user acceptance testing.

### Production
The final, polished version of the application is released to users. Automated pipelines ensure smooth deployment, version management, and continuous updates.

---

## DTAP Workflow in Jabberpoint

Jabberpoint implements a dedicated workflow for each phase of the DTAP lifecycle using GitHub Actions to ensure seamless automation:

### 1. **Development Workflow**
This workflow ensures the code remains clean, formatted, and compliant with standards.
- Runs when changes are pushed to the `dev` branch or a pull request targets `dev`.
- **Tasks:**
    - Checkout repository files.
    - Verify the presence of lock files.
    - Install dependencies using Maven while skipping tests.
    - Check coding style with Checkstyle and Spotless.
    - Upload reports for debugging style violations.

### 2. **Test Workflow**
Focused on validating application correctness through testing.
- Runs when changes target the `testing` branch or specific branches (`dev`, `acceptance`).
- **Tasks:**
    - Build the application with Maven.
    - Execute unit tests and integration tests.
    - Generate and upload coverage reports using JaCoCo for review.

### 3. **Acceptance Workflow**
Ensures the application is ready to meet user expectations.
- Runs when pull requests target the `acceptance` branch or changes are pushed to `acceptance`.
- **Tasks:**
    - Clone the full repository history.
    - Run language-specific linters using `super-linter` to ensure code quality.
    - Validate only the changed files for efficiency.

### 4. **Production Workflow**
Automates the final production build and deployment.
- Runs when changes are pushed to the `production` branch or pull requests target it.
- **Tasks:**
    - Build the application with Maven, ensuring a production-ready state.
    - Execute all tests to confirm stability.
    - Upload the packaged application artifact (e.g., `.jar`) for deployment.
    - Submit a dependency graph for tracking library updates and security.

These workflows automate key tasks, ensuring efficiency and reliability at every stage.

---

## Installation

### Prerequisites

Ensure you have the following installed before proceeding:
- **Java Development Kit (JDK)**: Version 21
- **Apache Maven**: Required to manage dependencies and build the project.

### Steps to Install

1. **Clone the Repository**  
   Clone the repository to your local system using:
   ```sh
   git clone git@github.com:izabelarybinska/softwareQuality.git

2. **Build the Project**:  
   Navigate to the directory containing the repository and execute the Maven build command:
   ```sh
   mvn clean install

3. **Run the Application**:  
   Launch Jabberpoint.
   


