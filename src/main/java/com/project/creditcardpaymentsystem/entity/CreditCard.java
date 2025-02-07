package com.project.creditcardpaymentsystem.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "credit_cards")
@Data
public class CreditCard {
    @Id
    private String id;
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate; // Format: MM/YY
    private String cvv; // last three-digit number in back side
    private double balance;
    private double spendingLimit; // field for spending limit
    private String cardType; // e.g., Visa, MasterCard
    private String customerId; // Link to Customer

    // New fields for card benefits
    private double cashbackPercentage; // e.g., 1.5 for 1.5% cashback
    private double rewardsPoints; // Total rewards points
    private String discounts; // Description of discounts available

    private LocalDate lastUsedDate; // New field to store the last used date
    private String status; // New field to track card status (e.g., active, lost, replaced, closed)
    // New field for credit score
    private int creditScore; // Credit score for the card

    // New field to indicate if the card is locked
    private boolean locked; // true if the card is locked, false otherwise
}