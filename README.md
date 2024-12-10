# Credit Card Payment System

## Overview
This project is a `Spring Boot` application designed to handle operations related to a credit card payment system. It uses a `MongoDB` database for data persistence and provides RESTful APIs for managing users, customers, credit cards, and transactions.

### Features
- **User Management**:
    - Create, update, delete, and search for users.
    - Associate users with customers and manage their active status.
- **Customer Management**:
    - Manage customer details such as name, email, phone, and address.
    - Link customers to their credit cards and transactions.
- **Credit Card Management**:
    - Store and retrieve credit card details like card number, card type, and expiration date.
- **Transaction Management**:
    - Log transactions with details like amount, currency, date, and status.

---

## Technologies Used
- **Spring Boot**: Framework for building the REST API.
- **MongoDB**: NoSQL database for data storage.
- **Lombok**: For reducing boilerplate code in entities.
- **Java 11/17**: Programming language.

---

## API Endpoints

### **User APIs**
| HTTP Method | Endpoint             | Description                       |
|-------------|----------------------|-----------------------------------|
| `GET`       | `/users`             | Retrieve all users.               |
| `GET`       | `/users/id/{id}`     | Retrieve a user by ID.            |
| `POST`      | `/users`             | Create a new user.                |
| `PUT`       | `/users/id/{id}`     | Update an existing user by ID.    |
| `DELETE`    | `/users/id/{id}`     | Delete a user by ID.              |


This project is a Spring Boot-based RESTful API for managing customers, credit cards, transactions, and users in a credit card payment system. The application uses MongoDB as the database to store all entities.

---

## Features
1. **User Management**
  - Create, read, update, and delete user accounts.
  - Secure storage of user information.
  - Associate users with customer profiles.

2. **Customer Management**
  - Create, read, update, and delete customers.
  - Manage customer details such as name, email, phone, and address.
  - Link customers with credit cards and transaction history.

3. **Credit Card Management**
  - Create, read, update, and delete credit card information.
  - Store details such as card number, cardholder name, expiration date, CVV, and card type.

4. **Transaction Management**
  - Create, read, update, and delete transactions.
  - Store transaction details like amount, currency, date, description, and status.

5. **RESTful API Endpoints**
  - Structured APIs for managing all entities with proper request-response handling.

6. **Technology Stack**
  - Spring Boot for backend development.
  - MongoDB as the database.
  - Lombok for boilerplate code reduction.

---

## API Endpoints

### **User Endpoints**
- `GET /users`: Fetch all users.
- `POST /users`: Create a new user.
- `GET /users/id/{id}`: Fetch a user by ID.
- `PUT /users/id/{id}`: Update user details by ID.
- `DELETE /users/id/{id}`: Delete a user by ID.

### **Customer Endpoints**
- `GET /customers`: Fetch all customers.
- `POST /customers`: Create a new customer.
- `GET /customers/{id}`: Fetch a customer by ID.
- `PUT /customers/{id}`: Update customer details by ID.
- `DELETE /customers/{id}`: Delete a customer by ID.

### **Credit Card Endpoints**
- `GET /creditcards`: Fetch all credit cards.
- `POST /creditcards`: Create a new credit card.
- `GET /creditcards/{id}`: Fetch a credit card by ID.
- `PUT /creditcards/{id}`: Update credit card details by ID.
- `DELETE /creditcards/{id}`: Delete a credit card by ID.

### **Transaction Endpoints**
- `GET /transactions`: Fetch all transactions.
- `POST /transactions`: Create a new transaction.
- `GET /transactions/{id}`: Fetch a transaction by ID.
- `PUT /transactions/{id}`: Update transaction details by ID.
- `DELETE /transactions/{id}`: Delete a transaction by ID.

---
