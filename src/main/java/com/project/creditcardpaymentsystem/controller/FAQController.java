package com.project.creditcardpaymentsystem.controller;

import com.project.creditcardpaymentsystem.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/faqs")
public class FAQController {

    @Autowired
    private FAQService faqService;

    @GetMapping
    public ResponseEntity<String> getFAQ(@RequestParam String question) {
        String response = faqService.getFAQResponse(question);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}