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

## Some notes

- Anemic domain is presented because currently does not have any functionality in the model object. The business logic
  is in the domain service.
- Application layer is in charged to have the use cases, calling the domain services... some managers could also be
  defined within it if many domain services would have to interchange information.
- In infrastructure, rest and repository layers have the responsability of mapping the domain object previous to execute
  any call into inner layers (as application or domain).
- Avoid of use lombok or mapstruct by using the Java API instead. I also could use it but I decided not to do it to take
  advantage of the language.
- Unit tests and Integration tests have been implemented.
- **IMPORTANT**: the data.sql file is loaded both for testing and starting the service.