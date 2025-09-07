<<<<<<< HEAD
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
=======
# chaos-game



## Getting started

To make it easy for you to get started with GitLab, here's a list of recommended next steps.

Already a pro? Just edit this README.md and make it your own. Want to make it easy? [Use the template at the bottom](#editing-this-readme)!

## Add your files

- [ ] [Create](https://docs.gitlab.com/ee/user/project/repository/web_editor.html#create-a-file) or [upload](https://docs.gitlab.com/ee/user/project/repository/web_editor.html#upload-a-file) files
- [ ] [Add files using the command line](https://docs.gitlab.com/topics/git/add_files/#add-files-to-a-git-repository) or push an existing Git repository with the following command:

```
cd existing_repo
git remote add origin https://gitlab.com/shekhe9920/chaos-game.git
git branch -M main
git push -uf origin main
```

## Integrate with your tools

- [ ] [Set up project integrations](https://gitlab.com/shekhe9920/chaos-game/-/settings/integrations)

## Collaborate with your team

- [ ] [Invite team members and collaborators](https://docs.gitlab.com/ee/user/project/members/)
- [ ] [Create a new merge request](https://docs.gitlab.com/ee/user/project/merge_requests/creating_merge_requests.html)
- [ ] [Automatically close issues from merge requests](https://docs.gitlab.com/ee/user/project/issues/managing_issues.html#closing-issues-automatically)
- [ ] [Enable merge request approvals](https://docs.gitlab.com/ee/user/project/merge_requests/approvals/)
- [ ] [Set auto-merge](https://docs.gitlab.com/user/project/merge_requests/auto_merge/)

## Test and Deploy

Use the built-in continuous integration in GitLab.

- [ ] [Get started with GitLab CI/CD](https://docs.gitlab.com/ee/ci/quick_start/)
- [ ] [Analyze your code for known vulnerabilities with Static Application Security Testing (SAST)](https://docs.gitlab.com/ee/user/application_security/sast/)
- [ ] [Deploy to Kubernetes, Amazon EC2, or Amazon ECS using Auto Deploy](https://docs.gitlab.com/ee/topics/autodevops/requirements.html)
- [ ] [Use pull-based deployments for improved Kubernetes management](https://docs.gitlab.com/ee/user/clusters/agent/)
- [ ] [Set up protected environments](https://docs.gitlab.com/ee/ci/environments/protected_environments.html)

***

# Editing this README

When you're ready to make this README your own, just edit this file and use the handy template below (or feel free to structure it however you want - this is just a starting point!). Thanks to [makeareadme.com](https://www.makeareadme.com/) for this template.

## Suggestions for a good README

Every project is different, so consider which of these sections apply to yours. The sections used in the template are suggestions for most open source projects. Also keep in mind that while a README can be too long and detailed, too long is better than too short. If you think your README is too long, consider utilizing another form of documentation rather than cutting out information.

## Name
Choose a self-explaining name for your project.

## Description
Let people know what your project can do specifically. Provide context and add a link to any reference visitors might be unfamiliar with. A list of Features or a Background subsection can also be added here. If there are alternatives to your project, this is a good place to list differentiating factors.

## Badges
On some READMEs, you may see small images that convey metadata, such as whether or not all the tests are passing for the project. You can use Shields to add some to your README. Many services also have instructions for adding a badge.

## Visuals
Depending on what you are making, it can be a good idea to include screenshots or even a video (you'll frequently see GIFs rather than actual videos). Tools like ttygif can help, but check out Asciinema for a more sophisticated method.

## Installation
Within a particular ecosystem, there may be a common way of installing things, such as using Yarn, NuGet, or Homebrew. However, consider the possibility that whoever is reading your README is a novice and would like more guidance. Listing specific steps helps remove ambiguity and gets people to using your project as quickly as possible. If it only runs in a specific context like a particular programming language version or operating system or has dependencies that have to be installed manually, also add a Requirements subsection.

## Usage
Use examples liberally, and show the expected output if you can. It's helpful to have inline the smallest example of usage that you can demonstrate, while providing links to more sophisticated examples if they are too long to reasonably include in the README.

## Support
Tell people where they can go to for help. It can be any combination of an issue tracker, a chat room, an email address, etc.

## Roadmap
If you have ideas for releases in the future, it is a good idea to list them in the README.

## Contributing
State if you are open to contributions and what your requirements are for accepting them.

For people who want to make changes to your project, it's helpful to have some documentation on how to get started. Perhaps there is a script that they should run or some environment variables that they need to set. Make these steps explicit. These instructions could also be useful to your future self.

You can also document commands to lint the code or run tests. These steps help to ensure high code quality and reduce the likelihood that the changes inadvertently break something. Having instructions for running tests is especially helpful if it requires external setup, such as starting a Selenium server for testing in a browser.

## Authors and acknowledgment
Show your appreciation to those who have contributed to the project.

## License
For open source projects, say how it is licensed.

## Project status
If you have run out of energy or time for your project, put a note at the top of the README saying that development has slowed down or stopped completely. Someone may choose to fork your project or volunteer to step in as a maintainer or owner, allowing your project to keep going. You can also make an explicit request for maintainers.
>>>>>>> 7620c2b23bbbc82fdda76ed43f7c782ae47ebd06
