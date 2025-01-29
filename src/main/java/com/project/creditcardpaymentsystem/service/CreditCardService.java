package com.project.creditcardpaymentsystem.service;


import com.project.creditcardpaymentsystem.entity.CardReplacementRequest;
import com.project.creditcardpaymentsystem.entity.CreditCard;
import com.project.creditcardpaymentsystem.entity.Customer;
import com.project.creditcardpaymentsystem.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private EmailService emailService; // Inject EmailService

    @Autowired
    private  CustomerService customerService;

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

    public String requestCardReplacement(CardReplacementRequest request) {
        Optional<CreditCard> creditCardOptional = creditCardRepository.findById(request.getCardId());
        if (creditCardOptional.isPresent()) {
            CreditCard creditCard = creditCardOptional.get();
            creditCard.setStatus("REPLACED"); // Update the status of the card
            creditCardRepository.save(creditCard); // Save the updated card status

            // Here you can add logic to generate a new card number and send it to the user
            // For simplicity, we will just return a success message
            return "Replacement card requested successfully. Your current card status is now 'REPLACED'.";
        }
        return "Credit Card not found.";
    }

    private static final int CREDIT_SCORE_THRESHOLD = 600; // Define a threshold for alerts
    private static final int SIGNIFICANT_CHANGE = 50; // Define what constitutes a significant change


    public Optional<CreditCard> updateCreditScore(String cardId, int newCreditScore) {
        Optional<CreditCard> creditCardOptional = creditCardRepository.findById(cardId);
        if (creditCardOptional.isPresent()) {
            CreditCard creditCard = creditCardOptional.get();
            int oldCreditScore = creditCard.getCreditScore();
            creditCard.setCreditScore(newCreditScore); // Update the credit score
            creditCardRepository.save(creditCard); // Save the updated credit card

            // Check for significant change or if below threshold
            if (Math.abs(oldCreditScore - newCreditScore) >= SIGNIFICANT_CHANGE || newCreditScore < CREDIT_SCORE_THRESHOLD) {
                sendCreditScoreAlert(creditCard, oldCreditScore, newCreditScore);
            }

            return Optional.of(creditCard);
        }
        return Optional.empty();
    }

    // New method to automatically update credit score based on transaction amount
    public void autoUpdateCreditScore(String cardId, double transactionAmount) {
        Optional<CreditCard> creditCardOptional = creditCardRepository.findById(cardId);
        if (creditCardOptional.isPresent()) {
            CreditCard creditCard = creditCardOptional.get();
            // Example logic: Increase credit score by 1 for every $100 spent
            int newCreditScore = creditCard.getCreditScore() + (int)(transactionAmount / 100);
            creditCard.setCreditScore(newCreditScore);
            creditCardRepository.save(creditCard);
        }
    }


    private void sendCreditScoreAlert(CreditCard creditCard, int oldCreditScore, int newCreditScore) {
        String subject = "Credit Score Alert";
        String body = String.format("Dear Customer,\n\n" +
                "Your credit score has changed.\n" +
                "Old Credit Score: %d\n" +
                "New Credit Score: %d\n\n" +
                "Please take necessary actions if needed.\n\n" +
                "Best regards,\n" +
                "Credit Card Payment System Team", oldCreditScore, newCreditScore);

        // Assuming you have a method to get the customer's email from the credit card
        String customerEmail = getCustomerEmailByCardId(creditCard.getId());
        emailService.sendTransactionNotification(customerEmail, subject, body);
    }

    private String getCustomerEmailByCardId(String cardId) {
        // Retrieve the credit card by its ID
        Optional<CreditCard> creditCardOptional = creditCardRepository.findById(cardId);
        if (creditCardOptional.isPresent()) {
            CreditCard creditCard = creditCardOptional.get();

            // Retrieve the customer associated with the credit card
            Optional<Customer> customerOptional = customerService.getById(creditCard.getCustomerId());
            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();
                return customer.getEmail(); // Return the customer's email
            }
        }
        return null; // Return null if no customer is found
    }

    public Optional<CreditCard> lockCreditCard(String cardId) {
        Optional<CreditCard> creditCardOptional = creditCardRepository.findById(cardId);
        if (creditCardOptional.isPresent()) {
            CreditCard creditCard = creditCardOptional.get();
            creditCard.setLocked(true); // Lock the card
            creditCardRepository.save(creditCard); // Save the updated card
            return Optional.of(creditCard);
        }
        return Optional.empty();
    }

    public Optional<CreditCard> unlockCreditCard(String cardId) {
        Optional<CreditCard> creditCardOptional = creditCardRepository.findById(cardId);
        if (creditCardOptional.isPresent()) {
            CreditCard creditCard = creditCardOptional.get();
            creditCard.setLocked(false); // Unlock the card
            creditCardRepository.save(creditCard); // Save the updated card
            return Optional.of(creditCard);
        }
        return Optional.empty();
    }
}
