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

        // Deduct amount from the credit card
        CreditCard creditCard = creditCardService.getById(transaction.getCreditCardId()).orElse(null);
        if (creditCard != null) {
            if (creditCard.getBalance() >= transaction.getAmount()) {
                creditCard.setBalance(creditCard.getBalance() - transaction.getAmount()); // Deduct the amount
                creditCardService.saveCreditCard(creditCard); // Save updated credit card

                // Retrieve the customer associated with the credit card
                Customer customer = customerService.getById(creditCard.getCustomerId()).orElse(null);
                if (customer != null) {
                    // Save the transaction
                    transactionRepository.save(transaction); // Save transaction

                    // Send email notification
                    String emailBody = String.format("Dear %s,\n\n" +
                                    "Your transaction of amount %.2f %s has been successfully processed.\n" +
                                    "Transaction ID: %s\n" +
                                    "Credit Card ID: %s\n" +
                                    "Transaction Date: %s\n" +
                                    "Status: %s\n\n" +
                                    "Thank you for using our service!\n" +
                                    "Best regards,\n" +
                                    "Credit Card Payment System Team",
                            customer.getName(), transaction.getAmount(), transaction.getCurrency(),
                            transaction.getId(), creditCard.getId(), transaction.getTransactionDate(), transaction.getStatus());

                    emailService.sendTransactionNotification(customer.getEmail(), "Transaction Notification", emailBody);
                } else {
                    throw new RuntimeException("Customer not found.");
                }
            } else {
                throw new RuntimeException("Insufficient balance on the credit card.");
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