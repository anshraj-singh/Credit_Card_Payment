package com.project.creditcardpaymentsystem.entity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "customers")
@Data
public class Customer {
    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;

    @DBRef
    private List<CreditCard> creditCards; // Link to CreditCard entities

    @DBRef
    private List<Transaction> transactions; // Link to Transaction entities
}
