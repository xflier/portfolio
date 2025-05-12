# User Authentication System

This project is a user authentication system built to demonstrate secure login, registration, and user/role management.

## Features

- User registration with email and password
- Secure password hashing
- User login with JWT Token generation and refresh the token
- Stateless for Rest APIs
- Role-based access control 

## Technologies Used

- **Backend**: Spring Boot Framework
- **Database**: RDBMS, for example PostgreSQL, it should be set up separated, check the postgres directory for an example
- **Authentication**: Basic Auth or JWT 
- **Frontend**: None
- **JDK version**: JDK 21

## Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/xflier/user-auth-n.git
    cd user-auth-n
    ```

2. Install dependencies via Maven and package it under target directory - jar file
    ```bash
    mvn package -DskipTests
    ```

## Usage

- Access the application at `http://localhost:8080`.
- Register a new user or log in with existing credentials.

## Contact

For questions or support, please contact [xflier@yahoo.com].