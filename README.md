# Tariff Service - Fernando Álvarez

Technical assessment for Inditex Core Platform.

Spring Boot version 3.4.1
Java version 17

The aim is to create a service with one endpoint to find the correct tariff to apply to a product at a given datetime.

## How to start

To start the service you just need to compile the project and then run it. The application properties included has all
the necessary to make the project run.

1. Download the code.
2. Perform a `mvn clean install`
3. Then run it with `mvn spring-boot:run`

The sample data is going to be inserted into the H2 InMemory Database from the data.sql file defined in
src/main/resources with the aim to have some data included.