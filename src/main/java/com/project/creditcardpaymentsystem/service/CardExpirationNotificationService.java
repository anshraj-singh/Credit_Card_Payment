package com.project.creditcardpaymentsystem.service;

import com.project.creditcardpaymentsystem.entity.CreditCard;
import com.project.creditcardpaymentsystem.entity.Customer;
import com.project.creditcardpaymentsystem.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CardExpirationNotificationService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmailService emailService;

    // Scheduled task to check for expiring cards
    @Scheduled(cron = "0 0 12 * * ?") // Runs every day at noon
    public void checkForExpiringCards() {
        LocalDate today = LocalDate.now();
        LocalDate warningDate = today.plusMonths(1); // Notify 1 month before expiration

        List<CreditCard> expiringCards = creditCardRepository.findByExpirationDateBetween(today, warningDate);
        for (CreditCard card : expiringCards) {
            Customer customer = customerService.getById(card.getCustomerId()).orElse(null);
            if (customer != null) {
                sendExpirationNotification(customer, card);
            }
        }
    }

    private void sendExpirationNotification(Customer customer, CreditCard card) {
        String emailBody = String.format("Dear %s,\n\n" +
                        "This is a reminder that your credit card ending in %s is set to expire on %s.\n" +
                        "Please take the necessary steps to renew your card to avoid any interruptions in service.\n\n" +
                        "Best regards,\n" +
                        "Credit Card Payment System Team",
                customer.getName(), card.getCardNumber().substring(card.getCardNumber().length() - 4), card.getExpirationDate());

        emailService.sendTransactionNotification(customer.getEmail(), "Credit Card Expiration Reminder", emailBody);
    }
}