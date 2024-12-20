package com.project.creditcardpaymentsystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "customers")
@Data
@NoArgsConstructor
public class Customer {
    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;

    private List<String> creditCardIds = new ArrayList<>(); // Store CreditCard IDs
    private List<String> transactionIds = new ArrayList<>(); // Store Transaction IDs
}