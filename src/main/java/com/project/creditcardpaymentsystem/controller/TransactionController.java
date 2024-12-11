package com.project.creditcardpaymentsystem.controller;

import com.project.creditcardpaymentsystem.entity.CreditCard;
import com.project.creditcardpaymentsystem.entity.Transaction;
import com.project.creditcardpaymentsystem.service.CreditCardService;
import com.project.creditcardpaymentsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CreditCardService creditCardService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> allTransactions = transactionService.findAllTransactions();
        if (!allTransactions.isEmpty()) {
            return new ResponseEntity<>(allTransactions, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        try {
            CreditCard creditCard = creditCardService.getById(transaction.getCreditCard().getId()).orElse(null);
            if (creditCard != null) {
                transaction.setCreditCard(creditCard);
                transactionService.saveTransaction(transaction);
                return new ResponseEntity<>(transaction, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable String myId) {
        Optional<Transaction> transaction = transactionService.getById(myId);
        if(transaction.isPresent()){
            return new ResponseEntity<>(transaction.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateTransaction(@PathVariable String myId, @RequestBody Transaction updatedTransaction) {
        Transaction existingTransaction = transactionService.getById(myId).orElse(null);
        if (existingTransaction != null) {
            if (updatedTransaction.getAmount() > 0) {
                existingTransaction.setAmount(updatedTransaction.getAmount());
            }
            if (updatedTransaction.getStatus() != null && !updatedTransaction.getStatus().isEmpty()) {
                existingTransaction.setStatus(updatedTransaction.getStatus());
            }
            transactionService.saveTransaction(existingTransaction);
            return new ResponseEntity<>(existingTransaction, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable String myId) {
        transactionService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}