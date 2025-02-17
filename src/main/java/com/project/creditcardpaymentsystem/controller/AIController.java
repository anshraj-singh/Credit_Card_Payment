package com.project.creditcardpaymentsystem.controller;

import com.project.creditcardpaymentsystem.service.GeminiIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AIController {

    @Autowired
    private GeminiIntegrationService geminiIntegrationService;

    @PostMapping("/generate-content")
    public ResponseEntity<String> generateContent(@RequestBody String prompt) {
        String generatedContent = geminiIntegrationService.generateContent(prompt);
        return new ResponseEntity<>(generatedContent, HttpStatus.OK);
    }
}