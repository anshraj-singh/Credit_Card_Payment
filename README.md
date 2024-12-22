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

The Credit Card Payment System is a Spring Boot-based application designed 
to manage users, customers, credit cards, and transactions. It integrates 
MongoDB as the database to store and retrieve information efficiently. The 
project provides a robust backend for managing credit card payment workflows,
supporting CRUD operations for users, customers, credit cards, and 
transactions.

# Features

 * User Management: Create, read, update, and delete user accounts.

 * Customer Management: Link users to customers and manage customer details such as name, email, phone, and address.

 * Credit Card Management: Store and manage credit card details, including card number, holder name, expiration date, and card type.

 * Transaction Management: Track transactions linked to customers and credit cards, including amount, currency, description, and status.

 * MongoDB Relationships: Fully integrated data relationships using @DBRef to link collections efficiently.


## API Endpoints

### Create Credit Card

- **Endpoint:** `POST /credit-cards`
- **Request Body:**
    ```json
    {
        "cardNumber": "4111111111111111",
        "cardHolderName": "John Doe",
        "expirationDate": "12/25",
        "cvv": "123",
        "cardType": "Visa",
        "customerId": "customerId123"  
    }
    ```
- **Response:**
  - **201 Created**: Returns the created credit card object.
  - **400 Bad Request**: If the customer is not found or if validation fails.

### Process Transaction

- **Endpoint:** `POST /transactions`
- **Request Body:**
    ```json
    {
        "amount": 100.00,
        "creditCardId": "creditCardId123",
        "description": "Purchase at Store"
    }
    ```
- **Response:**
  - **201 Created**: Returns the transaction details.
  - **400 Bad Request**: If the credit card is invalid or insufficient funds.

### Example Request for Transaction

bash
curl -X POST "http://localhost:8080/transactions" \
-H "Content-Type: application/json" \
-d '{
    "amount": 100.00,
    "creditCardId": "creditCardId123",
    "description": "Purchase at Store"
}'

# Credit Card Payment System [UPDATE]

This project implements a Credit Card Payment System with secure user authentication and authorization. It uses **Spring Boot** for the backend, **MongoDB** for the database, and **Spring Security** for securing the endpoints.

## Table of Contents

- [Overview](#overview)
- [Entities](#entities)
  - [CreditCard](#creditcard)
  - [Transaction](#transaction)
  - [Customer](#customer)
- [Controllers](#controllers)
  - [CreditCardController](#creditcardcontroller)
  - [TransactionController](#transactioncontroller)
- [Services](#services)
  - [CreditCardService](#creditcardservice)
  - [TransactionService](#transactionservice)
- [Security Configuration](#security-configuration)
- [API Endpoints](#api-endpoints)
- [Setup](#setup)
- [Technologies](#technologies)

## Overview

The Credit Card Payment System allows users to:

1. Create and manage customers and their credit cards.
2. Process transactions securely.
3. Retrieve and update transaction details.

The system is secured using **Basic Authentication** and **Spring Security** to control access to sensitive endpoints.

---

## Entities

### CreditCard

The `CreditCard` entity represents a credit card with fields such as `cardNumber`, `cardHolderName`, `expirationDate`, `cvv`, and a reference to the associated customer.

### Transaction

The `Transaction` entity represents a transaction made with a credit card. It includes details like `amount`, `currency`, `transactionDate`, and `status`.

### Customer

The `Customer` entity represents a customer in the system. It contains basic customer information like `name`, `email`, and lists of associated credit card IDs and transaction IDs.

---

## Controllers

### CreditCardController

Handles CRUD operations for credit cards. The controller includes methods to fetch, create, update, and delete credit cards.

### TransactionController

Manages transactions. It includes methods for retrieving all transactions, creating new transactions, updating existing ones, and deleting transactions.

---

## Services

### CreditCardService

Provides services to handle CRUD operations for credit cards, including saving, fetching, and deleting credit card information.

### TransactionService

Handles transaction management, including saving transactions, fetching transaction details, and deleting transactions.

---

## Security Configuration

The system is secured using **Spring Security**. Basic Authentication is applied to the endpoints, ensuring that only authenticated users can access sensitive data. The system does not use role-based access control but ensures that only authenticated users can access the APIs related to credit cards and transactions.

---

## API Endpoints

- **GET /credit-cards**: Retrieve all credit cards.
- **POST /credit-cards**: Create a new credit card.
- **GET /credit-cards/id/{id}**: Retrieve a credit card by ID.
- **PUT /credit-cards/id/{id}**: Update a credit card by ID.
- **DELETE /credit-cards/id/{id}**: Delete a credit card by ID.

- **GET /transactions**: Retrieve all transactions.
- **POST /transactions**: Create a new transaction.
- **GET /transactions/id/{id}**: Retrieve a transaction by ID.
- **PUT /transactions/id/{id}**: Update a transaction by ID.
- **DELETE /transactions/id/{id}**: Delete a transaction by ID.

---


## Credit Card Payment System[UPDATE]

### Features

1. **Admin Role-Based Access Control:**
  - Introduced a dedicated `/admin` endpoint for admin users to access restricted data.
  - Only users with the `ROLE_ADMIN` role can access the `/admin/**` endpoints.
  - Utilized Spring Security for role-based access control.

2. **Admin Endpoints:**
  - **`GET /admin/all-customers`**:
    - Fetches all customers from the database.
    - Only accessible by users with the `ROLE_ADMIN` role.
    - Returns a list of all customers with a `200 OK` or `404 Not Found` if no customers exist.

3. **Security Enhancements:**
  - Configured **Spring Security** to restrict access:
    - `/admin/**` endpoints are restricted to users with `ROLE_ADMIN`.
    - Other endpoints like `/Users/me`, `/customers/**`, and `/transactions/**` require authentication.
    - Basic Authentication is implemented for simplicity.
  - Passwords are stored securely with `BCryptPasswordEncoder`.

4. **Service Refactoring:**
  - Introduced a `CustomerService` to fetch all customers, decoupling database operations from controllers.
  - Ensures modularity and better code organization.

5. **Repository Updates:**
  - Added a `CustomerRepository` interface for accessing customer data.

### Endpoints Summary

#### **Admin Endpoints**
- **`GET /admin/all-customers`**
  - **Description:** Fetches a list of all customers in the system.
  - **Access Control:** Restricted to users with `ROLE_ADMIN`.
  - **Response Examples:**
    - **200 OK:**
      ```json
      [
        {
          "id": "customer123",
          "name": "John Doe",
          "email": "johndoe@example.com"
        },
        {
          "id": "customer124",
          "name": "Jane Smith",
          "email": "janesmith@example.com"
        }
      ]
      ```
    - **404 Not Found:**
      ```json
      {
        "message": "No customers found."
      }
      ```

#### **Security Configuration**
- `/admin/**` → Accessible by `ROLE_ADMIN`.
- `/Users/me`, `/customers/**`, `/transactions/**` → Accessible to authenticated users.

### How to Test
1. **Start the Server:**
  - Ensure the application is running on the appropriate port (e.g., `http://localhost:8080`).
2. **Admin Authentication:**
  - Use **Basic Auth** in Postman (admin credentials are required).
3. **Endpoint Testing:**
  - Test `/admin/all-customers` for fetching all customers.
  - Confirm restricted access for non-admin users.

## Transaction Processing Update
TransactionService:

- Implemented functionality to deduct the transaction amount from the credit card balance during transaction processing.
- Added checks for sufficient balance before completing a transaction.


TransactionController:

- Enhanced error handling to provide meaningful feedback for insufficient funds or when a credit card is not found.
- Updated response messages to improve user experience.


## Email Notifications Feature Update

### Introduction

The recent update to the Email Notifications feature enhances communication with customers regarding their transactions. Customers will now receive immediate email notifications after a transaction is processed, ensuring they are informed about their financial activities.

### Key Features

- **Automatic Email Notifications**: Customers receive an email notification immediately after a transaction is processed.
- **Email Content**: The email includes:
    - The amount of the transaction.
    - A confirmation that the transaction has been processed.
    - A subject line indicating it is a transaction notification.

### Implementation Details

1. **Transaction Entity**:
    - The `Transaction` entity has been updated to remove the `customerEmail` field. The email is now derived from the associated `Customer` entity.

2. **Email Service**:
    - The `EmailService` class is responsible for sending email notifications using `JavaMailSender`.

3. **Transaction Service Logic**:
    - When a transaction is saved, the system checks the associated credit card for sufficient balance.
    - Upon successful transaction processing, the system retrieves the customer's email from the `Customer` entity linked to the credit card.
    - An email notification is sent to the customer using the `EmailService`.

### Error Handling

- **Insufficient Balance**: If the credit card does not have enough balance, an exception is thrown, and no email is sent.
- **Customer Not Found**: If the customer associated with the credit card cannot be found, an appropriate error message is logged.

### Testing the Feature

1. **Create a Transaction**: Test the feature by creating a transaction through the API.
2. **Verify Email Receipt**: Check the email inbox of the customer to ensure the notification is received with the correct details.
3. **Monitor Logs**: Review application logs for any errors related to email sending or transaction processing.

### Conclusion

This update to the Email Notifications feature significantly improves customer engagement by providing timely updates on transaction statuses. The implementation ensures that customers are informed promptly, enhancing their overall experience with the Credit Card Payment System.
