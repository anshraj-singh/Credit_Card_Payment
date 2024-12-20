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
    private CreditCardService creditCardService; // Add CreditCardService

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
                transactionRepository.save(transaction); // Save transaction
            } else {
                throw new RuntimeException("Insufficient balance on the credit card.");
            }
        } else {
            throw new RuntimeException("Credit Card not found.");
        }
    }

    public Optional<Transaction> getById(String myId){
        return transactionRepository.findById(myId);
    }

    public void deleteById(String myId){
        transactionRepository.deleteById(myId);
    }
}
