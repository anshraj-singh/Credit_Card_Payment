package com.project.creditcardpaymentsystem.entity;

import lombok.Data;

@Data
public class ProductRequest {
    private String name; // Product name
    private long amount; // Amount in cents
    private long quantity; // Quantity of the product
    private String currency; // Currency code (e.g., "usd")
}