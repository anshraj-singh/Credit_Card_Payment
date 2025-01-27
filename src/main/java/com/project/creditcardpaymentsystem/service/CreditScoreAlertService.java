package com.project.creditcardpaymentsystem.service;

import com.project.creditcardpaymentsystem.entity.CreditCard;
import com.project.creditcardpaymentsystem.entity.User;
import com.project.creditcardpaymentsystem.repository.CreditCardRepository;
import com.project.creditcardpaymentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditScoreAlertService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService; // Service to send email notifications

    // Threshold for significant score change
    private static final int SCORE_CHANGE_THRESHOLD = 50;

    // Method to check credit scores and send alerts
    @Scheduled(cron = "0 0 * * * ?") // Check every hour
    public void checkCreditScores() {
        List<CreditCard> creditCards = creditCardRepository.findAll();

        for (CreditCard creditCard : creditCards) {
            User user = userRepository.findById(creditCard.getCustomerId()).orElse(null);
            if (user != null) {
                // Check if the score has changed significantly
                int currentScore = creditCard.getCreditScore();
                int previousScore = getPreviousCreditScore(creditCard.getId()); // Implement this method to retrieve the previous score

                if (Math.abs(currentScore - previousScore) >= SCORE_CHANGE_THRESHOLD) {
                    sendCreditScoreAlert(user, currentScore);
                }
            }
        }
    }

    private void sendCreditScoreAlert(User user, int currentScore) {
        String subject = "Credit Score Alert";
        String body = String.format("Dear %s,\n\nYour credit score has changed to %d.\n\nBest regards,\nCredit Card Payment System Team",
                user.getUsername(), currentScore);

        // Send email notification to the first customer's email
        if (!user.getCustomers().isEmpty()) {
            String customerEmail = user.getCustomers().get(0).getEmail();
            emailService.sendTransactionNotification(customerEmail, subject, body);
        }
    }

    private int getPreviousCreditScore(String creditCardId) {
        // Implement logic to retrieve the previous credit score from a database or cache
        // This is a placeholder implementation
        return 700; // Replace with actual logic
    }
}