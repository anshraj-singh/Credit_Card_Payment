package com.project.creditcardpaymentsystem.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "card_replacements")
@Data
public class CardReplacementRequest {
    @Id
    private String id; // Unique identifier for the replacement request
    private String cardId; // ID of the card to be replaced
    private String reason; // Reason for replacement (e.g., lost, stolen)
    private LocalDateTime requestDate; // Date when the request was made
    private String status; // Status of the request (e.g., PENDING, COMPLETED)
}