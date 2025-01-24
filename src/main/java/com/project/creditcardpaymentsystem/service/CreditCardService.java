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

    // New method to get card benefits
    public String getCardBenefits(String cardId) {
        Optional<CreditCard> creditCardOptional = getById(cardId);
        if (creditCardOptional.isPresent()) {
            CreditCard creditCard = creditCardOptional.get();
            return String.format("Card Type: %s\nCashback: %.2f%%\nRewards Points: %.0f\nDiscounts: %s",
                    creditCard.getCardType(),
                    creditCard.getCashbackPercentage(),
                    creditCard.getRewardsPoints(),
                    creditCard.getDiscounts());
        }
        return "Credit Card not found.";
    }
}
