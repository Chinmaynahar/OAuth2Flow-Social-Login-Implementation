OAuth2 Social Login & Microservices Platform

Overview

This project implements a secure authentication and authorization platform using Spring Boot, Spring Authorization Server, and OAuth2 / OpenID Connect social login (Google, GitHub).

The system is designed to support microservices, JWT-based security, and API Gateway integration, following real-world enterprise authentication architecture.

High-Level Architecture
Roles
Component	Responsibility
Auth Service	Authorization Server + OAuth2 Client
Social Providers	External Identity Providers (Google / GitHub)
User Service	Resource Server
MySQL (Docker)	Persistent storage
Frontend	Login initiation & API consumption

Authentication Flow (Summary)

User initiates login via Google/GitHub

Social provider authenticates the user

Auth Service receives authorization code

Auth Service issues its own access & refresh tokens

Frontend uses platform JWTs to access APIs

Social provider tokens are used only for identity verification, not for securing internal APIs.

Tech Stack

Java 17

Spring Boot

Spring Security

Spring Authorization Server

OAuth 2.0 / OpenID Connect

JWT

MySQL (Docker)


Maven

Docker & Docker Compose

How to Run the Project (Local Setup)
Prerequisites

Ensure the following are installed:

Java 17+

Maven

Docker & Docker Compose

Git

Step 1: Clone the Repository

`git clone https://github.com/Chinmaynahar/OAuth2Flow-Social-Login-Implementation.git`

`cd project-root`

Step 2: Start MySQL Using Docker

The project uses MySQL via Docker 

Start MySQL container

Ensure:

MySQL port is exposed (default: 3306)

Database credentials match application.yml

Step 3: Configure Application Properties

Update the following files as needed:

auth-service/src/main/resources/application.yml

MySQL credentials

OAuth2 client credentials (Google/GitHub)

MySQL

JWT issuer / public key

Step 4: Run Auth Service 

cd auth-service

mvn spring-boot:run

Default port (example):

http://localhost:9000


Step 5: Test OAuth2 Flow (Backend)

Use Postman:

Authorization Code flow

Token endpoint

Refresh token grant

✔ Token issuance works correctly when tested via Postman

⚠️ Project Status & Known Issues

This project is under active development.

1. OAuth2 Frontend Integration Issue

OAuth2 login completes successfully on the backend

Tokens are generated correctly and verified via Postman

Frontend is currently not receiving access/refresh tokens

Current State

Social login redirects correctly

Authorization code exchange works

Backend issues platform tokens

Frontend token handling is incomplete

Possible Causes

OAuth2 redirect callback not finalized

Token delivery strategy not implemented:

HTTP-only cookies vs JSON response

Frontend OAuth2 flow incomplete

📌 Backend functionality is verified

📌 Frontend integration is pending

2. Eureka Dependency Cleanup

Eureka configuration was marked optional in application.yml

However, Eureka client dependencies still exist in pom.xml

These dependencies are no longer required and will be removed

This is acknowledged as technical debt, not a functional issue.

Help Wanted (Frontend OAuth2 Integration)

Contributions are welcome, especially from developers with experience in:

OAuth2 Authorization Code Flow

Frontend OAuth2 callback handling

Secure token storage strategies

Spring Security + frontend integration

If you can help:

Open an issue

Submit a pull request

Suggest improvements

Verified Working Components

✔ Spring Authorization Server 

✔ OAuth2 Social Login (Google / GitHub)

✔ JWT Access Token issuance

✔ Refresh Token flow (Postman tested)

✔ Resource Server token validation

✔ MySQL via Docker

Roadmap

Finalize frontend OAuth2 integration

Remove unused Eureka dependencies

Improve documentation


License

MIT License

Author

Chinmay Nahar
