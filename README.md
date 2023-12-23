# cars-01-project

This project helps to manage cars. Cars contain information about model, price, price, color, mileage,
and components. You can simply add orders from .json, .txt files as well as from MySql database.
Due to implemented `abstract factory` design pattern there is an easy way to implement more sources from where you can
add your cars.
Project comes up with functionality of registering users to database, security and signing in.

# Set-up
- Java 21,
- Spring,
- Hibernate
- Maven,
- Docker,
- Spark,
- Jjwt,
- Gson,
- Simplejavamail,
- Junit,
- Mockito,
- Lombok,
- Hamcrest,
- Jdbi.

# Software design approach
- Domain Driven Design

# Design patterns
- Abstract factory
- Observer
- Builder
- Singleton

# How to install?

Use command below to install the package into your local repository.
```
mvn clean install
```
If you don't want to create local repository use:
```
mvn clean package -DskipTests
```

# How to run?

Run with docker:
```
docker-compose up -d --build mysql_main cars_01_web_app_ddd
```
Application image from dockerhub will be downloaded.

# Running unit tests

To run unit tests properly go to project destination in terminal and then execute below command:
```
docker-compose up -d --build mysql_test
```
This will download MySql image from dockerhub based on docker-compose.yml file, create test database and fill with
values that are required for unit tests. To run unit tests type:
```
mvn test
```

# Change data source

To change data source to .json, .txt or db go to [application.properties](src/main/resources/application.properties) file and choose source, that you want to use.

# Sending mails

To send mails properly go to [application.properties](src/main/resources/application.properties) and fill the values with proper data.
You can also modify activation email subject and email content inside this .properties file.

Due to implemented `observer` design pattern after sending mail to a person, the notification about sent mail 
is also being sent to chosen mail.

# Abstract factory

Abstract factory is based in [factory](src/main/java/com/app/domain/cars_management/policy/factory).
To create objects using abstract factory you have to fill [application.properties](api/src/main/resources/application.properties) file config.
Choose what processor type you want to use: db, json or txt.

Serialization/deserialization data from .json files is made using `Gson` library. For reading purposes you can find
use of gson library in [Loader](src/main/java/com/app/domain/cars_management/policy/factory/loader). For .txt serialization/deserialization stream methods are used and can be found
inside the same loader path as for .json format. Library responsible for working with database is Jdbi library.

# Docker-compose

Docker-compose file provides three containers:
- test database,
- main database,
- application.

# Application
# Domain
# Infrastructure