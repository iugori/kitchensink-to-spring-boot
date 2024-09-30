- [Modernizing a JavaEE application (2024)](#modernizing-a-javaee-application-2024)
  - [Quickstart](#quickstart)
  - [Project highlights](#project-highlights)
    - [Folder structure](#folder-structure)
    - [Technologies](#technologies)
  - [Detailed workspace configuration](#detailed-workspace-configuration)
  - [References](#references)


# Modernizing a JavaEE application (2024)

This is a migration of the *kitchensink* JBoss demo application to Spring Boot / Angular stack.

![kitchensink application homepage](ops/doc/pic/kitchensink-form.jpg#center)

The application features a three-field registration form that allows the addition of new members (with proper validation). 
Below the form there is the list of all the registered members ordered alphabetically by name with a REST URL allowing the extraction of their individual details in JSON format.
At the bottom of the page there is another REST URL allowing the retrieval of the entire member list in JSON format.

The purpose of the migration was to preserve the original's application functionality to the fullest extent feasible.

## Quickstart

**Requirements**:
- *Docker*
- *git* utility (e.g., [Git for Windows](https://git-scm.com/downloads/win) + Git Bash)
- *make* utility (e.g., [GNU Make for Windows](https://gnuwin32.sourceforge.net/packages/make.htm))

**Steps to take**:

Before continuing, please note that the following commands will create 2 docker images
<ins>ks-back-image</ins> and <ins>ks-front-image</ins> and 2 containers <ins>ks-back</ins> and <ins>ks-front</ins>.
Please make sure you do not already have similar resources in your Docker installation because *they might be overwritten*.

- Please run in a Linux like shell:
  - `git clone https://github.com/iugori/kitchensink-to-spring-boot.git`
  - `cd kitchensink-to-spring-boot`
  - `make demo`
- and then navigate to http://localhost:8910 .

To visit the API documentation, you can navigate to http://localhost:8911/swagger-ui.html .

## Project highlights

### Folder structure

- *ks-back* - contains the Spring Boot application source code
- *ks-front* - contains the Angular application source code
- *ops* - contains various maintenance resources (e.g., configuration and deployment scripts and also documentation resources)
- *qa* - contains black-box testing solutions:
  - *api* - a Java project containing REST Assured based tests for validating the REST API behavior
  - *ui* - a Cypress project for testing the UI
  - Remark: both test suites run successfully on both the legacy application and the newly developed one
- root resources
  - *Makefile* - provides a consolidated interface for local deployment scripts

### Technologies

- Back-end
  - OpenJDK 21
  - Spring Boot 3.3.4
  - JPA 
  - REST
  - OpenAPI 3
  - H2 - in memory SQL database
  - JUnit 5 + Mockito
- Front-end
  - Angular 18
  - template based forms
  - SCSS style sheets
  - TypeScript 5.4
- QA
  - White-box 
    - @DataJpaTest - for back-end repositories
    - @ExtendWith(MockitoExtension.class) - for back-end services
    - @SpringBootTest - for back-end context creation
  - Black-box
    - API: REST Assured (+ Lombok + JavaFaker) based test suites
    - UI: Cypress based test suites
- Deployment
  - Docker containers
  - Nginx for wrapping Angular application
  - Makefile based configuration and deployment management

## Detailed workspace configuration

The development was performed on Windows 11. The recommended shell is GitBash.

**Utilities to be installed:**
- Docker (optional for development)
- Git for Windows + Git Bash v2.46.2.windows.1 +
- GNU Make for Windows (optional for development)
- Maven ~3.9.9
- Node.js v20.17.0
- OpenJDK 21
- Visual Studio Code 

**Environment variables to be configured:**
- PATH - should contain at least 
  - C:\Windows\System32
  - make (e.g., ...\GnuWin32\bin)
  - mvn (e.g., ...\apache-maven-3.9.9\bin)
  - ng (e.g., ...\Roaming\npm)
  - node (e.g., ...\nodejs)
  - java (e.g., ...\jdk-21\bin)
- JAVA_HOME environment variables set (e.g., ...\jdk-21)

**Running the application:**
- in *ks-back*
  - `mvn spring-boot:run`
    - this will start the Spring Boot application on port 8081
- in *ks-front*
  - `npm install`
  - `npm start`
    - this will start the Angular application
    - if the browser page does not open automatically, you can try navigating to http://localhost:4200

## References

- [The original *kitchensink* application repository](https://github.com/jboss-developer/jboss-eap-quickstarts/tree/8.0.x/kitchensink) 


