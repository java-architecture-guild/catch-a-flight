# Catch a Flight

## Modular Monolith Variant

### Architecture

### Tech Stack

- Java 24
- Spring Boot 3.5
- REST / GraphQL
- Spring Security
- Spring Authorization Server
- Spring Data JDBC
- Spring Data JPA
- H2 Database

### Modules

#### Account

Manages user identities, secures access to the system, and handles subscription details, ensuring a personalized and
protected experience.

#### BestDeal

Helps users discover the most affordable flight options by analyzing real-time data and highlighting top-value
fares for various destinations.

#### Booking

Simplifies the flight reservation process, allowing users to search for flights, select their preferred seats and travel 
extras, and securely complete their payment in one seamless flow.

#### Engagement

Captures user interactions and feedback to deliver personalized content, targeted promotions, and timely notifications 
that enhance the overall user experience

#### Order

Handles the entire booking lifecycle after a user has paid, managing ticket generation, itinerary details, 
and any subsequent modifications or cancellations.

#### Query

Processes and fulfills user search requests for flights, using various criteria like origin, destination, 
and dates to present relevant and up-to-date flight information.

#### Common

Provides a set of reusable functionalities and shared data, such as currency conversion, time zone management, 
and universal design components, to ensure consistency across the entire system.

#### Shared-kernel

Contains a small, highly-cohesive subset of the domain model, such as core entities or value objects, that are critical 
for communication and collaboration between different bounded contexts.

#### Application

Serves as the main entry point for the application, orchestrating the initialization and integration of all other 
modules to launch and run the entire system.

#### Api Simulator

Mimics the behavior of external APIs, allowing developers to test system functionality in a controlled environment 
without relying on live services.
