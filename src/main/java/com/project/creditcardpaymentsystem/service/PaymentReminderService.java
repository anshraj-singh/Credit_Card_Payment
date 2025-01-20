package com.project.creditcardpaymentsystem.service;

import com.project.creditcardpaymentsystem.entity.Transaction;
import com.project.creditcardpaymentsystem.entity.User;
import com.project.creditcardpaymentsystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentReminderService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    // Scheduled method to send payment reminders
    @Scheduled(cron = "0 0 9 * * ?") // Every day at 9 AM
    public void sendPaymentReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminderTime = now.plusDays(1); // Set reminder for 1 day before due date

        List<Transaction> transactions = transactionRepository.findAll();
        for (Transaction transaction : transactions) {
            if (transaction.getDueDate() != null && transaction.getDueDate().isEqual(reminderTime.toLocalDate().atStartOfDay())) {
                User user = userService.getById(transaction.getCreditCardId()).orElse(null); // Assuming creditCardId is linked to User
                if (user != null && !user.getCustomers().isEmpty()) {
                    String email = user.getCustomers().get(0).getEmail(); // Send to the first customer's email
                    String subject = "Payment Reminder";
                    String body = "Dear " + user.getCustomers().get(0).getName() + ",\n\n" +
                            "This is a reminder that your payment of " + transaction.getAmount() + " " + transaction.getCurrency() +
                            " is due tomorrow.\n" +
                            "Transaction ID: " + transaction.getId() + "\n" +
                            "Due Date: " + transaction.getDueDate() + "\n\n" +
                            "Please ensure that you make the payment on time to avoid any late fees.\n\n" +
                            "Thank you!";
                    emailService.sendTransactionNotification(email, subject, body);
                }
            }
        }
    }
}