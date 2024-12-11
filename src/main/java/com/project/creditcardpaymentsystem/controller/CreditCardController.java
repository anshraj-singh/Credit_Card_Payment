package com.project.creditcardpaymentsystem.controller;


import com.project.creditcardpaymentsystem.entity.CreditCard;
import com.project.creditcardpaymentsystem.entity.Customer;
import com.project.creditcardpaymentsystem.service.CreditCardService;
import com.project.creditcardpaymentsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/credit-cards")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CreditCard>> getAllCreditCards() {
        List<CreditCard> allCreditCards = creditCardService.findAllCreditCards();
        if (!allCreditCards.isEmpty()) {
            return new ResponseEntity<>(allCreditCards, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createCreditCard(@RequestBody CreditCard creditCard) {
        try {
            creditCardService.saveCreditCard(creditCard);
            return new ResponseEntity<>(creditCard, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating credit card: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("id/{myId}")
    public ResponseEntity<CreditCard> getCreditCardById(@PathVariable String myId) {
        Optional<CreditCard> creditCard = creditCardService.getById(myId);
        if(creditCard.isPresent()){
            return new ResponseEntity<>(creditCard.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<CreditCard> updateCreditCard(@PathVariable String myId, @RequestBody CreditCard updatedCreditCard) {
        CreditCard existingCreditCard = creditCardService.getById(myId).orElse(null);
        if (existingCreditCard != null) {
            if (updatedCreditCard.getCardHolderName() != null && !updatedCreditCard.getCardHolderName().isEmpty()) {
                existingCreditCard.setCardHolderName(updatedCreditCard.getCardHolderName());
            }
            if (updatedCreditCard.getCardNumber() != null && !updatedCreditCard.getCardNumber().isEmpty()) {
                existingCreditCard.setCardNumber(updatedCreditCard.getCardNumber());
            }
            if (updatedCreditCard.getExpirationDate() != null && !updatedCreditCard.getExpirationDate().isEmpty()) {
                existingCreditCard.setExpirationDate(updatedCreditCard.getExpirationDate());
            }
            creditCardService.saveCreditCard(existingCreditCard);
            return new ResponseEntity<>(existingCreditCard, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<Void> deleteCreditCard(@PathVariable String myId) {
        creditCardService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}