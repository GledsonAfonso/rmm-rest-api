# RMM REST API

API for basic inventory of Devices and Services to calculate their total costs for a Remote Monitoring and Management (RMM) platform.

## Pre-requisites

You will need to have installed on your machine the following:

- Java 17
- Maven 3.8.4 (or use the embedded one that comes with the project!)

## Running the application locally

Before running the project, you will need to build it. To do that, just type:

```bash
./mvnw clean install
```

With that out of the way, you can now run the application by typing:

```bash
java -jar ./target/rmmrestapi.jar
```

## Endpoints

### Postman collection

If you wish to test the endpoints, you can do so by using Postman. The link below will set you up to use the present endpoints in the application:

```text
https://www.getpostman.com/collections/93040f51b2466a6363d7
```

For further reading about the project's documentation, please see the [wiki](https://github.com/GledsonAfonso/rmm-rest-api/wiki).
