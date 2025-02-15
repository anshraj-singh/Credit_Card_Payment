package com.project.creditcardpaymentsystem.service;

import com.project.creditcardpaymentsystem.entity.CardReplacementRequest;
import com.project.creditcardpaymentsystem.repository.CardReplacementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardReplacementService {

    @Autowired
    private CardReplacementRepository cardReplacementRepository;

    // Method to retrieve all card replacement requests
    public List<CardReplacementRequest> findAllRequests() {
        return cardReplacementRepository.findAll();
    }
}