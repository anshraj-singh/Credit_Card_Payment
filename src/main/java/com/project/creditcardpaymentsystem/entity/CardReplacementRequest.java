package com.project.creditcardpaymentsystem.entity;

import lombok.Data;

@Data
public class CardReplacementRequest {
    private String cardId; // ID of the card to be replaced
    private String reason; // Reason for replacement (e.g., lost, stolen)
}