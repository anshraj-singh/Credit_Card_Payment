package com.project.creditcardpaymentsystem.controller;

import com.project.creditcardpaymentsystem.entity.CreditCard;
import com.project.creditcardpaymentsystem.entity.Customer;
import com.project.creditcardpaymentsystem.entity.Transaction;
import com.project.creditcardpaymentsystem.entity.User;
import com.project.creditcardpaymentsystem.service.CreditCardService;
import com.project.creditcardpaymentsystem.service.CustomerService;
import com.project.creditcardpaymentsystem.service.TransactionService;
import com.project.creditcardpaymentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> allTransactions = transactionService.findAllTransactions();
        if (!allTransactions.isEmpty()) {
            return new ResponseEntity<>(allTransactions, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        try {
            CreditCard creditCard = creditCardService.getById(transaction.getCreditCardId()).orElse(null);
            if (creditCard != null) {
                transaction.setCreditCardId(creditCard.getId());

                // Set the due date for the transaction (e.g., 30 days from now)
                transaction.setDueDate(LocalDateTime.now().plusDays(30)); // Set due date to 30 days from now

                transactionService.saveTransaction(transaction);

                // Find the customer associated with the credit card
                Customer customer = customerService.getById(creditCard.getCustomerId()).orElse(null);
                if (customer != null) {
                    customer.getTransactionIds().add(transaction.getId());
                    customerService.saveCustomer(customer);
                }
                return new ResponseEntity<>(transaction, HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Credit Card not found", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error creating transaction: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating transaction: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable String myId) {
        Optional<Transaction> transaction = transactionService.getById(myId);
        return transaction.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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

    // Search transactions by year range and amount
    @GetMapping("/search")
    public ResponseEntity<List<Transaction>> searchTransactions(
            @RequestParam String startDate,
            @RequestParam String endDate) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        if (user != null) {
            List<Transaction> allTransactions = transactionService.findAllTransactions();
            List<Transaction> filteredTransactions = allTransactions.stream()
                    .filter(transaction -> {
                        LocalDateTime transactionDate = transaction.getTransactionDate();
                        LocalDateTime start = LocalDateTime.parse(startDate);
                        LocalDateTime end = LocalDateTime.parse(endDate);
                        return (transactionDate.isEqual(start) || transactionDate.isAfter(start)) &&
                                (transactionDate.isEqual(end) || transactionDate.isBefore(end)) &&
                                user.getCustomers().stream()
                                        .flatMap(customer -> customer.getTransactionIds().stream())
                                        .anyMatch(id -> id.equals(transaction.getId()));
                    })
                    .collect(Collectors.toList());

            return new ResponseEntity<>(filteredTransactions, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
