package com.project.creditcardpaymentsystem.service;

import com.project.creditcardpaymentsystem.api.response.GeminiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class GeminiIntegrationService {

    @Value("${external.gemini.api.key}")
    private String apiKey;

    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=GEMINI_API_KEY";

    @Autowired
    private RestTemplate restTemplate;

    public String generateContent(String prompt) {
        String finalUrl = API_URL.replace("GEMINI_API_KEY", apiKey);

        // Request Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // Request Body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", Collections.singletonList(
                Collections.singletonMap("parts", Collections.singletonList(
                        Collections.singletonMap("text", prompt)
                ))
        ));

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // API Call
        ResponseEntity<GeminiResponse> response = restTemplate.exchange(finalUrl, HttpMethod.POST, requestEntity, GeminiResponse.class);

        // Extract Response
        if (response.getBody() != null && response.getBody().getCandidates() != null) {
            return response.getBody().getCandidates().get(0).getContent().getParts().get(0).getText();
        }

        return "No response from Gemini API";
    }
}