package com.project.creditcardpaymentsystem.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "credit_cards")
@Data
public class CreditCard {
    @Id
    private String id;
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate; // Format: MM/YY
    private String cvv; // last three-digit number in back side
    private String cardType; // e.g., Visa, MasterCard
    private String customerId; // Link to Customer
}