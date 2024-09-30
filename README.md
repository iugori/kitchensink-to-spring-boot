- [Modernizing a JavaEE application (2024)](#modernizing-a-javaee-application-2024)
  - [Quickstart](#quickstart)
  - [Migration highlights](#migration-highlights)
    - [Technologies](#technologies)
    - [Folder structure](#folder-structure)
    - [QA pointers](#qa-pointers)
  - [Detailed workspace configuration](#detailed-workspace-configuration)
  - [Referenceas](#referenceas)


# Modernizing a JavaEE application (2024)

This is a migration of the [kitchensink](https://github.com/jboss-developer/jboss-eap-quickstarts/tree/8.0.x/kitchensink) JBoss demo application to Spring Boot / Angular stack.

![application form](ops/doc/pic/kitchensink-form.jpg#center)

The application features a three field registration form that allows the addition of new members (with proper validation). 
Below the form there is the list of all the registered members ordered alphabetically by name with a REST URL allowing the extraction of their individual details in JSON format.
At the bottom of the page there is another REST URL allowing the retrieval of the entire member list in JSON format.

The purpose of the migration was to preserve the original's application functionality to the fullest extent feasible.

## Quickstart
Requirements:
- *Docker*
- *git* utility (e.g. [Git for Windows](https://git-scm.com/downloads/win) + Git Bash)
- *make* utility (e.g. [GNU Make for Windows](https://gnuwin32.sourceforge.net/packages/make.htm))

Steps:
- Please run in a Linux like shell:
  - git clone https://github.com/iugori/kitchensink-to-spring-boot.git
  - cd kitchensink-to-spring-boot
  - make demo
- and then navigate to http://localhost:8910

To visit the API documentation you can navigate to http://localhost:8911/swagger-ui.html

## Migration highlights

### Technologies

### Folder structure

### QA pointers

## Detailed workspace configuration

## Referenceas



