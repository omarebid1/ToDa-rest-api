*** To run this project, it's required .env file for each service ***

This project is a microservices-based ToDo application built using Spring Boot. It consists of two independent services:

1-  ToDo Service:
          Responsible for managing ToDo items with full CRUD operations. Each ToDo item is associated with a specific user.

2-  User Service:
          Handles user registration, login, and profile management. It provides JWT-based authentication and authorization to secure communication between the client and services.

Each service has its own dedicated database and communicates over HTTP. JWT tokens are used to authorize requests from clients to the ToDo service.
