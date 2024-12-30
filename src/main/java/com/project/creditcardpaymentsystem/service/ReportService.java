package com.project.creditcardpaymentsystem.service;

import com.project.creditcardpaymentsystem.entity.Transaction;
import com.project.creditcardpaymentsystem.entity.User;
import com.project.creditcardpaymentsystem.entity.Customer; // Import Customer entity
import com.project.creditcardpaymentsystem.service.EmailService;
import com.project.creditcardpaymentsystem.service.TransactionService;
import com.project.creditcardpaymentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    public void generateMonthlyReport(String userId, String customerId, int month, int year) {
        User user = userService.getById(userId).orElse(null);
        if (user != null) {
            // Find the customer by ID
            Customer customer = user.getCustomers().stream()
                    .filter(c -> c.getId().equals(customerId))
                    .findFirst()
                    .orElse(null);

            if (customer != null) {
                List<Transaction> transactions = transactionService.findAllTransactions()
                        .stream()
                        .filter(transaction -> transaction.getTransactionDate().getMonthValue() == month &&
                                transaction.getTransactionDate().getYear() == year &&
                                transaction.getCreditCardId() != null && // Ensure credit card ID is not null
                                customer.getCreditCardIds().contains(transaction.getCreditCardId()))
                        .collect(Collectors.toList());

                String report = createReport(transactions, month, year);
                emailService.sendTransactionNotification(customer.getEmail(), "Monthly Spending Report", report);
            } else {
                throw new RuntimeException("Customer not found.");
            }
        } else {
            throw new RuntimeException("User  not found.");
        }
    }

    private String createReport(List<Transaction> transactions, int month, int year) {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("Monthly Spending Report for ").append(month).append("/").append(year).append("\n\n");
        double totalSpent = 0;

        for (Transaction transaction : transactions) {
            reportBuilder.append("Date: ").append(transaction.getTransactionDate())
                    .append(", Amount: ").append(transaction.getAmount())
                    .append(", Description: ").append(transaction.getDescription())
                    .append("\n");
            totalSpent += transaction.getAmount();
        }

        reportBuilder.append("\nTotal Spent: ").append(totalSpent);
        return reportBuilder.toString();
    }
}