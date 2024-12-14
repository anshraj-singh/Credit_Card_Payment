package com.project.creditcardpaymentsystem.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "transactions")
@Data
public class Transaction {
    @Id
    private String id;
    private double amount;
    private String currency;
    private LocalDateTime transactionDate;
    private String description;
    private String status; // PENDING, COMPLETED, FAILED
    private String creditCardId; // Link to CreditCard
    private String customerId; // Link to Customer
}