package com.project.creditcardpaymentsystem.service;

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

    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    public void saveTransaction(Transaction transaction) {
        transaction.setTransactionDate(LocalDateTime.now()); // Set current date and time
        transaction.setStatus("COMPLETED"); // Set default status
        transactionRepository.save(transaction);
    }

    public Optional<Transaction> getById(String myId){
        return transactionRepository.findById(myId);
    }

    public void deleteById(String myId){
        transactionRepository.deleteById(myId);
    }
}
