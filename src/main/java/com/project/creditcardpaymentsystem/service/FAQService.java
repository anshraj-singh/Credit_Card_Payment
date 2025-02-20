package com.project.creditcardpaymentsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FAQService {

    @Autowired
    private GeminiIntegrationService geminiIntegrationService;

    public String getFAQResponse(String question) {
        // Use the Gemini API to generate a response based on the question
        return geminiIntegrationService.generateContent(question);
    }
}