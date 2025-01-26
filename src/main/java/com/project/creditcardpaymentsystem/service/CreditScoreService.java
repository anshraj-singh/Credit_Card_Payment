package com.project.creditcardpaymentsystem.service;

import com.project.creditcardpaymentsystem.entity.CreditCard;
import com.project.creditcardpaymentsystem.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditScoreService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    public Optional<Integer> getCreditScore(String creditCardId) {
        Optional<CreditCard> creditCardOptional = creditCardRepository.findById(creditCardId);
        return creditCardOptional.map(CreditCard::getCreditScore);
    }
}