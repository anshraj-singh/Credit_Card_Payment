package com.project.creditcardpaymentsystem.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StripeResponse {
    private String status; // Status of the response
    private String message; // Message from the response
    private String sessionId; // ID of the created session
    private String sessionUrl; // URL to redirect for payment
}