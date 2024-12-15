package com.project.creditcardpaymentsystem.service;


import com.project.creditcardpaymentsystem.entity.CreditCard;
import com.project.creditcardpaymentsystem.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    public List<CreditCard> findAllCreditCards() {
        return creditCardRepository.findAll();
    }

    public void saveCreditCard(CreditCard creditCard) {
        creditCardRepository.save(creditCard);
    }

    public Optional<CreditCard> getById(String myId) {
        return creditCardRepository.findById(myId);
    }

    public void deleteById(String myId) {
        creditCardRepository.deleteById(myId);
    }
}
