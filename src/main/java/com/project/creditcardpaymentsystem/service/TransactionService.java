package com.project.creditcardpaymentsystem.service;

import com.project.creditcardpaymentsystem.entity.CreditCard;
import com.project.creditcardpaymentsystem.entity.Customer;
import com.project.creditcardpaymentsystem.entity.Transaction;
import com.project.creditcardpaymentsystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmailService emailService;

    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    public void saveTransaction(Transaction transaction) {
        transaction.setTransactionDate(LocalDateTime.now()); // Set current date and time
        transaction.setStatus("COMPLETED"); // Set default status

        // Retrieve the credit card associated with the transaction
        CreditCard creditCard = creditCardService.getById(transaction.getCreditCardId()).orElse(null);
        if (creditCard != null) {
            // Check if the transaction amount exceeds the spending limit
            if (transaction.getAmount() <= creditCard.getSpendingLimit()) {
                // Proceed with the transaction
                // Deduct the amount from the credit card balance
                if(creditCard.getBalance() > 0){
                    creditCard.setBalance(creditCard.getBalance() - transaction.getAmount()); // Deduct the amount
                }
                // Deduct from spending limit
                if (creditCard.getSpendingLimit() > 0) {
                    creditCard.setSpendingLimit(creditCard.getSpendingLimit() - transaction.getAmount()); // Deduct from spending limit
                }

                // Save updated credit card
                creditCardService.saveCreditCard(creditCard);

                // Save the transaction
                transactionRepository.save(transaction); // Save transaction

                // Retrieve the customer associated with the credit card
                Customer customer = customerService.getById(creditCard.getCustomerId()).orElse(null);
                if (customer != null) {
                    // Send email notification
                    String emailBody = String.format("Dear %s,\n\n" +
                                    "Your transaction of amount %.2f %s has been successfully processed.\n" +
                                    "Transaction ID: %s\n" +
                                    "Credit Card ID: %s\n" +
                                    "Transaction Date: %s\n" +
                                    "Status: %s\n\n" +
                                    "Your current credit card balance is: %.2f\n\n" + // Added current balance
                                    "Thank you for using our service!\n" +
                                    "Best regards,\n" +
                                    "Credit Card Payment System Team",
                            customer.getName(), transaction.getAmount(), transaction.getCurrency(),
                            transaction.getId(), creditCard.getId(), transaction.getTransactionDate(), transaction.getStatus(),
                            creditCard.getBalance()); // Include current balance in the email

                    emailService.sendTransactionNotification(customer.getEmail(), "Transaction Notification", emailBody);
                } else {
                    throw new RuntimeException("Customer not found.");
                }
            } else {
                throw new RuntimeException("Transaction amount exceeds spending limit.");
            }
        } else {
            throw new RuntimeException("Credit Card not found.");
        }
    }

    public Optional<Transaction> getById(String myId) {
        return transactionRepository.findById(myId);
    }

    public void deleteById(String myId) {
        transactionRepository.deleteById(myId);
    }
}
