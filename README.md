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

## Customer Creation Confirmation Email Feature

This feature sends a confirmation email to users upon successful account creation. The email includes the user's details and a plain text password.

### Implementation Steps

1. **Email Service Setup**:
    - Ensure the `spring-boot-starter-mail` dependency is included in your `pom.xml`.
    - Configure email properties in `application.properties`.

2. **Email Service Class**:
    - Create a service class to handle sending emails using `JavaMailSender`.

3. **Customer Creation Logic**:
    - Modify the customer creation logic to send a confirmation email after a new customer is created.

4. **Security Considerations**:
    - Avoid sending plain text passwords in emails. Consider sending a password reset link instead.

5. **Testing**:
    - Test the email functionality to ensure users receive confirmation emails with their details.

### Example Code Snippet

Here’s a brief example of how the email sending functionality is integrated:

```java
@Autowired
private EmailService emailService;

@PostMapping("/customers")
public ResponseEntity<?> createCustomer(@RequestBody Customer newCustomer) {
    // Save customer and send confirmation email
    emailService.sendConfirmationEmail(newCustomer.getEmail(), subject, body);
}
```

## Update: Credit Card Limit Management Feature

### Overview
The Credit Card Limit Management feature has been enhanced to ensure that the spending limit is correctly enforced during transactions. 

### Changes Made
- **Spending Limit Deduction**: After a successful transaction, the transaction amount is now deducted from both the credit card's balance and the spending limit. This ensures that users cannot exceed their set spending limits.

### Implementation Details
- The `spendingLimit` field in the `CreditCard` entity is now updated during the transaction processing in the `TransactionService`.
- The transaction processing logic checks if the transaction amount exceeds the spending limit before allowing the transaction to proceed.

### Testing
- Ensure to test the following scenarios:
  1. Transactions below the spending limit should succeed and deduct the amount from both the balance and the spending limit.
  2. Transactions equal to the spending limit should succeed and deduct the amount from both the balance and the spending limit.
  3. Transactions above the spending limit should fail as expected.
  4. Multiple transactions should correctly update the spending limit after each transaction.

## Monthly Spending Reports

### Feature Description

The Monthly Spending Reports feature allows users to generate a report summarizing their transactions for a specific month. The report is sent to the customer's email address and includes details such as transaction dates, amounts, descriptions, and the total spent for the month.

### How to Use

1. **Generate Monthly Report**:
    - Endpoint: `POST /reports/monthly`
    - Parameters:
        - `userId`: The ID of the user requesting the report.
        - `customerId`: The ID of the customer for whom the report is generated.
        - `month`: The month for which the report is generated (1-12).
        - `year`: The year for which the report is generated (e.g., 2024).

   **Example Request**:
   ```http
   POST http://localhost:8080/reports/monthly
   Content-Type: application/x-www-form-urlencoded

   userId=<user-id>&customerId=<customer-id>&month=12&year=2024


## Password Reset Functionality Update

### Overview

This update introduces the `PasswordResetController`, which manages the password reset process for users. It enhances security and user experience by allowing users to reset their passwords through a token-based system.

### Features

- **Password Reset Request**: Users can request a password reset by providing their registered username.

- **Token Generation**: A secure token is generated and sent to the user's associated customer's email for verification.

- **Password Update**: Users can update their password using the token, ensuring that only authorized requests are processed.

### Implementation Details

- **Controller**: The `PasswordResetController` handles the logic for sending reset emails and updating passwords.

- **Routes**:
    - **Request Password Reset**:
        - **Endpoint**: `/password/reset-request`
        - **Method**: `POST`
        - **Parameters**: `username`

    - **Reset Password**:
        - **Endpoint**: `/password/reset`
        - **Method**: `POST`
        - **Parameters**: `token`, `newPassword`

- **Email Service**: Integrated an email service to send password reset links to users.

### Usage

1. **Request Password Reset**: Users can initiate a password reset by accessing the `/password/reset-request` endpoint with their username.

2. **Reset Password**: After receiving the email, users can reset their password by accessing the `/password/reset` endpoint with the token and the new password.

### Example Requests

- **Request Password Reset**:
  ```bash
  curl -X POST "http://localhost:8080/password/reset-request?username=newuser"


## Password Reset Functionality Update

### Overview

This update introduces the `PasswordResetController`, which manages the password reset process for users. It enhances security and user experience by allowing users to reset their passwords through a token-based system.

### Features

- **Password Reset Request**: Users can request a password reset by providing their registered username. The reset link will be sent to the email of the first associated customer.

- **Token Generation**: A secure token is generated and sent to the user's associated customer's email for verification.

- **Password Update**: Users can update their password using the token, ensuring that only authorized requests are processed.

### Implementation Details

- **Controller**: The `PasswordResetController` handles the logic for sending reset emails and updating passwords.

- **Routes**:
    - **Request Password Reset**:
        - **Endpoint**: `/password/reset-request`
        - **Method**: `POST`
        - **Authentication**: Basic Auth (credentials of the logged-in user)

    - **Reset Password**:
        - **Endpoint**: `/password/reset`
        - **Method**: `POST`
        - **Parameters**:
            - `token`: The password reset token received via email.
            - `newPassword`: The new password to set.

### Example Requests

- **Request Password Reset**:
    - **URL**: `http://localhost:8080/password/reset-request`
    - **Method**: `POST`
    - **Authorization**: Basic Auth (use the credentials of the logged-in user)

    - **Expected Response**:
        - Success:
          ```json
          {
            "message": "Password reset link sent to your email."
          }
          ```
        - Failure:
          ```json
          {
            "message": "User  or associated customer not found."
          }
          ```

- **Reset Password**:
    - **URL**: `http://localhost:8080/password/reset`
    - **Method**: `POST`
    - **Body** (form-data or x-www-form-urlencoded):
        - `token`: `YOUR_RESET_TOKEN` (replace with the actual token received in the email)
        - `newPassword`: `newSecurePassword123` (the new password you want to set)

    - **Expected Response**:
        - Success:
          ```json
          {
            "message": "Password has been reset successfully."
          }
          ```
        - Failure:
          ```json
          {
            "message": "Invalid or expired token."
          }
          ```

### Email Notification

After successfully changing the password, an email is sent to the user with the following details:

- **Subject**: Your Password Has Been Updated
- **Body**: 
```
          Hello [Username],

          Your password has been successfully updated.
          Your new password is: [NewPassword]
```

## Payment Reminders Feature

### Overview
The Payment Reminders feature has been implemented to notify users of upcoming payment due dates for their transactions. This feature helps users avoid late fees and manage their payments effectively.

### Implementation Steps

1. **Scheduled Task**:
    - Utilizes Spring's scheduling capabilities to check for upcoming payment due dates daily at 9 AM.
    - The `PaymentReminderService` class is responsible for this task.

2. **Email Notifications**:
    - Sends automated email reminders to customers using the existing `EmailService`.
    - Reminders are sent one day before the payment due date, providing users with timely notifications.

### Email Notifications

The following email notifications are sent to customers as part of the Payment Reminders feature:

* **Payment Reminder Email**:
    - **Subject**: "Payment Reminder"
    - **Body**:
      ```
      Dear [Customer Name],
  
      This is a reminder that your payment of [Amount] [Currency] is due tomorrow.
      Transaction ID: [Transaction ID]
      Due Date: [Due Date]
  
      Please ensure that you make the payment on time to avoid any late fees.
  
      Thank you!
      ```

### Scheduled Email Messages

The following scheduled email messages are sent to customers as part of the Payment Reminders feature:

* **Daily Payment Reminder**:
    - **Schedule**: Daily at 9 AM
    - **Message**: Sends a payment reminder email to customers with upcoming payment due dates.

### Example
When a transaction is created with a due date, the system will automatically send an email reminder to the user the day before the payment is due.

### Email Service Configuration

The `EmailService` is configured to send emails using the following settings:

* **Email Server**: [Email Server URL]
* **Email Port**: [Email Port Number]
* **Email Username**: [Email Username]
* **Email Password**: [Email Password]

### Note
Please ensure that the email service configuration is updated with the correct settings to send emails successfully.

## Card Benefits Overview Feature

This feature allows users to view the benefits associated with their credit cards, including cashback, rewards points, and discounts.

### Implementation Details

- **Cashback Percentage**: Users can see the percentage of cashback they will receive on eligible purchases.
- **Rewards Points**: The total number of rewards points accumulated on the card is displayed.
- **Discounts**: A description of any discounts available to the cardholder is provided.

### API Endpoint

- **GET /credit-cards/benefits/{cardId}**: This endpoint retrieves the benefits associated with a specific credit card.

### Example Response

```json
{
    "status": "success",
    "data": {
        "cardType": "Master Card",
        "cashbackPercentage": 1.5,
        "rewardsPoints": 200,
        "discounts": "10% off at select retailers"
    }
}

```

## Card Replacement Requests Feature

This feature allows users to request a replacement card if their current card is lost or stolen. 

### Implementation Details

- **Endpoint**: `POST /credit-cards/replace-card`
- **Request Body**: The request should include the following JSON structure:

```json
{
    "cardId": "your_card_id_here",
    "reason": "Lost" 
}
```

# Credit Card Payment System

## Features

This project is a credit card payment system that allows users to manage their credit cards, transactions, and customers.

### New Feature: Credit Card Score

- **Credit Card Score**: Each credit card now has an associated credit score, which can be checked by the user. The score is a numeric value that reflects the creditworthiness of the cardholder.

### API Endpoints

#### Get Credit Card Score

- **Endpoint**: `GET /credit-cards/score/{cardId}`
- **Description**: Retrieves the credit score for a specific credit card.
- **Request Parameters**:
    - `cardId`: The ID of the credit card for which the score is being requested.
- **Response**:
    - **200 OK**: Returns the credit score.
    - **404 Not Found**: If the credit card does not exist.
    - **403 Forbidden**: If the user does not have permission to access the credit card.

### Example Request

```http
GET /credit-cards/score/12345
Example Response
{
    "score": 750
}
```

## Credit Score Alerts Feature

This feature sends notifications to customers when their credit score changes significantly or falls below a certain threshold.

### Implementation Steps

1. **Threshold Definition**: A threshold for credit score alerts is defined. The current threshold is set at **600**.
2. **Credit Score Update Method**: A method `updateCreditScore` checks the credit score and updates it. If the score changes significantly (by **50** points or more) or falls below the threshold, an alert is triggered.
3. **Email Notification**: The `sendCreditScoreAlert` method sends an email notification to the customer, informing them of the change in their credit score.

### Email Notification Format

When a credit score alert is triggered, the email sent to the customer is structured as follows:

```java
String subject = "Credit Score Alert";
String body = String.format("Dear Customer,\n\n" +
        "Your credit score has changed.\n" +
        "Old Credit Score: %d\n" +
        "New Credit Score: %d\n\n" +
        "Please take necessary actions if needed.\n\n" +
        "Best regards,\n" +
        "Credit Card Payment System Team", oldCreditScore, newCreditScore);
```

## Credit Card Lock/Unlock Feature

This feature allows users to temporarily lock or unlock their credit card in case of loss or theft.

### Implementation Details

- **Locking a Credit Card**: Users can lock their credit card to prevent any transactions from being processed. This is useful in situations where the card is lost or suspected to be stolen.

- **Unlocking a Credit Card**: Users can unlock their credit card when they find it or if they no longer suspect it to be compromised.

### API Endpoints

- **Lock Credit Card**
    - **Endpoint**: `PUT /credit-cards/lock/{cardId}`
    - **Description**: Locks the specified credit card.
    - **Response**: Returns the locked credit card details or a 404 status if the card is not found.

- **Unlock Credit Card**
    - **Endpoint**: `PUT /credit-cards/unlock/{cardId}`
    - **Description**: Unlocks the specified credit card.
    - **Response**: Returns the unlocked credit card details or a 404 status if the card is not found.

## Credit Card Locking and Transaction Notifications

This feature allows users to temporarily lock their credit cards and prevents any transactions from being processed when the card is locked. If a user attempts to make a transaction with a locked credit card, the system will:

1. **Check if the Credit Card is Locked**: Before processing a transaction, the system checks if the associated credit card is locked.
2. **Send Notification**: If the credit card is locked, an email notification is sent to the user indicating that their transaction attempt has failed due to the card being locked. The notification includes:
    - The transaction amount
    - Transaction ID
    - Credit Card ID
    - Transaction Date
    - Status of the transaction (FAILED)
    - A message prompting the user to unlock their card to proceed with transactions.

### Example Email Notification

When a transaction is attempted with a locked credit card, the user receives an email formatted as follows:
```
Dear Customer,

Your transaction attempt of amount [amount] [currency] has failed because your credit card is currently locked. 
Transaction ID: [transactionId] 
Credit Card ID: [creditCardId] 
Transaction Date: [transactionDate] 
Status: FAILED

Please unlock your card to proceed with transactions.

Best regards, 
Credit Card Payment System Team
```

## Card Expiration Notifications

This feature sends notifications to users when their credit cards are about to expire. The system checks for credit cards nearing their expiration date and sends an email reminder to the user.

### Implementation Details

- **Scheduled Task**: A scheduled task runs daily to check for credit cards that are set to expire within the next month.
- **Notification**: If a credit card is found to be expiring soon, an email notification is sent to the user.

### Email Notification Format

When a credit card is nearing expiration, the user receives an email formatted as follows:

```
Dear [Customer Name],

This is a reminder that your credit card ending in [Last 4 Digits of Card] is set to expire on [Expiration Date]. Please take the necessary steps to renew your card to avoid any interruptions in service.

Best regards, Credit Card Payment System Team
```

### Example

For example, if a user's credit card is set to expire on January 30, 2025, the email would look like this:
```
Dear John Doe,

This is a reminder that your credit card ending in 1234 is set to expire on 2025-01-30. Please take the necessary steps to renew your card to avoid any interruptions in service.

Best regards, Credit Card Payment System Team
```

## Features

### Card Replacement

The Card Replacement feature allows users to request a replacement for their credit card in case it is lost, stolen, or damaged.

#### How It Works

1. **Request Submission**: Users can submit a card replacement request by providing the card ID and the reason for the replacement.
2. **Processing**: The system processes the request, updates the status of the existing card, and saves the replacement request in the database.
3. **Email Notification**: Upon successful processing of the request, an email confirmation is sent to the user with the details of the new card.

#### Email Format

When a card replacement request is processed, the user receives an email with the following format:
Subject: Card Replacement Confirmation

```
Dear [Customer Name],

Your request for a card replacement has been processed successfully. New Card Details: 
Card Number: **** **** **** [Last 4 Digits] 
Card Type: [Card Type] Status: REPLACED

If you did not request this change, please contact our support team immediately.
Best regards, Credit Card Payment System Team
```

### How to Use

To request a card replacement, send a POST request to the `/card-replacements` endpoint with the following JSON body:

```json
{
    "cardId": "your_card_id",
    "reason": "lost"
}
```

## Stripe Payment Integration

This project now includes integration with Stripe for handling payments. The following features have been implemented:

- **StripeService**: A service class that manages the creation of checkout sessions for products.
- **ProductRequest**: An entity that encapsulates the details of the product being purchased, including name, amount, quantity, and currency.
- **StripeResponse**: An entity that encapsulates the response from the Stripe API after creating a checkout session, including session ID and URL.
- **StripeController**: A controller that exposes an endpoint for initiating the checkout process.

### How to Use

To create a checkout session, send a POST request to `/payments/checkout` with a JSON body containing the product details:

```json
{
  "name": "Product Name",
  "amount": 2000,
  "quantity": 1,
  "currency": "usd"
}
```

## Features

### Inactive Credit Card Account Management

This feature automatically closes credit card accounts that have not been used for a specified period of time. If a credit card is inactive for more than 365 days, the card issuer will close the account to maintain security and manage resources effectively.

#### How It Works

1. **Last Used Date Tracking**: Each credit card tracks the last date it was used.
2. **Scheduled Task**: A scheduled task runs daily at midnight to check all credit cards.
3. **Account Closure**: If a credit card has not been used for over a year, its status is updated to "CLOSED".

### How to Use

- The system automatically manages inactive accounts, so no user action is required.
- Users will be notified via email if their account is closed due to inactivity.

## Stripe Payment Integration

This project now includes integration with Stripe for handling payments. The following features have been implemented:

- **StripeService**: A service class that manages the creation of checkout sessions for products and sends email notifications upon successful payment.
- **ProductRequest**: An entity that encapsulates the details of the product being purchased, including name, amount, quantity, and currency.
- **StripeResponse**: An entity that encapsulates the response from the Stripe API after creating a checkout session, including session ID and URL.
- **StripeController**: A controller that exposes an endpoint for initiating the checkout process.

### Email Notification

When a payment is successfully completed, a notification email is sent to the customer with the following format:

```
Subject: Payment Successful
Body: Dear Customer,
      Your payment has been successfully processed. 
      Session ID: {sessionId} 
      Amount: {amount} {currency} 
      Thank you for your purchase!

      Best regards, Your Company Name
```

## Card Lock/Unlock Notifications

### Feature Overview
The Card Lock/Unlock Notifications feature sends email notifications to customers whenever their credit card is locked or unlocked. This feature enhances user experience by keeping customers informed about the status of their credit cards.

### Implementation Details
- **Class Modified**: `CreditCardService`
- **Functionality**:
    - When a card is locked, an email notification is sent to the customer informing them of the action.
    - When a card is unlocked, a similar notification is sent to confirm the action.

### Email Format
The email notifications are formatted as follows:

#### Card Lock Notification
```
Subject: Your Credit Card Has Been Locked
Dear [Customer Name],
Your credit card ending in [Last 4 Digits] has been successfully locked. If you did not 
request this change, please contact our support team immediately.

Best regards, Credit Card Payment System Team
```

#### Card Unlock Notification
```
Subject: Your Credit Card Has Been Unlocked
Dear [Customer Name],
Your credit card ending in [Last 4 Digits] has been successfully unlocked. You can now 
use your card for transactions.

Best regards, Credit Card Payment System Team
```
