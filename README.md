# Portfolio Project IDATG2003 - 2024

Welcome to this project repository for the "Fractal Generator" application developed as part of the System Development course at NTNU Gjøvik. The "Fractal Generator" is developed to demonstrate our skills in software development, including teamwork, project management, and software engineering principles. This document provides an overview of the project, its structure, and how to run the application and tests.

## Project Description

In this project, we have developed a desktop application called "Fractal Generator" that allows users to create, visualize, and manage fractals. The application enables users to add, edit, and delete fractal parameters, and visualize the generated fractals in a user-friendly interface.

Our team has implemented the application using JavaFX for the graphical user interface (GUI) and Java for the backend logic. The application features an intuitive interface with views for managing fractals and their parameters.

Some key features of the application include:
- Viewing predefined fractals
- Creating new fractals with customizable parameters
- Visualizing fractals with real-time updates
- Saving and loading fractals from files
- Scaling the viewing window
- Managing fractal parameters

## Project Structure

The project is organized into a modular architecture with separate packages for backend, frontend, and common utilities. Below is an overview of each package and its role in the application:

### Backend
Responsible for the application's business logic, data handling, and interaction with the file system.

- `chaosgame`: Core logic for generating fractals based on chaos game principles.
- `controllers`: Controls the application's flow and handles business logic.
- `engine`: Contains the engine components that run the fractal generation.
- `mathoperations`: Provides mathematical operations and utilities.
- `state`: Manages the application state.
- `transformations`: Defines transformations used in fractal generation.
- `utilitiesbackend`: Shared utilities across the backend, including configuration management and error handling.

### Frontend
Contains the user interface components and controllers to manage user interaction.

- `controllers`: Controls the flow of data in the GUI and handles user input.
- `utilityfrontend`: Reusable GUI components such as buttons, dialogs, and layouts.
- `view`: Defines the graphical user interface for different application features like home, settings, and fractal views.

### Common Utilities
Shared resources and utilities used by both frontend and backend.

- `commonutilities`: Shared utility classes and functions.

### Main Application Components

- `AppEnsemble`: The main class that launches the application and initializes all components.
- `MainApplication`: The entry point for the JavaFX application, setting up the primary stage and scene.

Each package is designed to function independently while interacting with each other to form a cohesive application.

## File Organization:
### Source Code


## How to Run the Project

Ensure you have Java 21 and Maven installed on your system to run the Fractal Generator application. Follow the steps below to get it up and running:

1. **Clone the repository**:
   Clone the project repository to your local machine using the following command in the terminal:
    ```sh
    git clone https://gitlab.stud.idi.ntnu.no/karwans/idatg1005_2024_10.git
    ```

2. **Navigate to the project directory**:
   Change to the project directory with this command:
    ```sh
    cd idatg1005_2024_10
    ```

3. **Build the project**:
   Build the project using Maven by running:
    ```sh
    mvn clean install
    ```

4. **Run the application**:
   Start the application with Maven's JavaFX plugin using:
    ```sh
    mvn javafx:run
    ```

## How to Run the Tests

Use the following Maven commands to run the tests within the project:

- **Run all tests**:
  Execute all tests with the following command:
    ```sh
    mvn test
    ```

- **Run specific test classes**:
  To run tests for a specific class, use this command (replacing `ClassName` with the actual test class name):
    ```sh
    mvn -Dtest=ClassName test
    ```

- **Run specific test methods**:
  To run a specific method within a test class, use this command (replacing `ClassName` with the test class name and `testMethodName` with the method name):
    ```sh
    mvn -Dtest=ClassName#testMethodName test
    ```

## Link to Repository

https://gitlab.stud.idi.ntnu.no/karwans/choas-game-gruppe-25-idatg2003