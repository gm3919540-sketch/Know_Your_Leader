Overview

This repository contains the backend services for the Know Your Leader platform.
The backend is responsible for managing political leader data, processing user queries, interacting with the database, and integrating AI APIs to generate summarized insights from public records.

The backend is built using Java and Spring Boot and follows RESTful API design principles.

Features

• RESTful APIs for managing political leader data
• Integration with AI APIs for generating summaries
• Hibernate ORM for efficient database operations
• Redis caching to optimize API performance
• Structured service architecture for scalability

Tech Stack Backend Framework,java,Spring Boot,Database,Oracle SQL,ORM,Hibernate,Caching,Redis,Other Tools,REST APIs

System Architecture
Client (React Frontend)->Spring Boot REST APIs->Redis Cache->Oracle SQL Database->->AI APIs for content summarization

API Functionalities

Example operations supported by the backend:

Fetch political leader profiles

Retrieve leader information by ID

Generate AI summaries for public records

Manage database CRUD operations
