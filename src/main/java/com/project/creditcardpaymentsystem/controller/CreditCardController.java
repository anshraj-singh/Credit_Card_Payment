package com.project.creditcardpaymentsystem.controller;

import com.project.creditcardpaymentsystem.entity.CreditCard;
import com.project.creditcardpaymentsystem.entity.Customer;
import com.project.creditcardpaymentsystem.entity.User;
import com.project.creditcardpaymentsystem.service.CreditCardService;
import com.project.creditcardpaymentsystem.service.CustomerService;
import com.project.creditcardpaymentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/credit-cards")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    // GET ALL CREDIT CARDS FOR LOGGED-IN USER
    @GetMapping
    public ResponseEntity<List<CreditCard>> getAllCreditCards() {
        String username = getAuthenticatedUsername();
        User user = userService.findByUsername(username);

        if (user != null && !user.getCustomers().isEmpty()) {
            // Retrieve all credit cards of the user's customers
            List<CreditCard> allCreditCards = user.getCustomers()
                    .stream()
                    .flatMap(customer -> customer.getCreditCardIds().stream())
                    .map(creditCardService::getById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

            return new ResponseEntity<>(allCreditCards, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // CREATE A NEW CREDIT CARD
    @PostMapping
    public ResponseEntity<?> createCreditCard(@RequestBody CreditCard creditCard, @RequestParam String customerId) {
        String username = getAuthenticatedUsername();
        User user = userService.findByUsername(username);

        if (user != null) {
            Optional<Customer> customerOptional = user.getCustomers()
                    .stream()
                    .filter(c -> c.getId().equals(customerId))
                    .findFirst();

            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();
                creditCard.setCustomerId(customerId);

                // Save credit card and update the customer
                creditCardService.saveCreditCard(creditCard);
                customer.getCreditCardIds().add(creditCard.getId());
                customerService.saveCustomer(customer);

                return new ResponseEntity<>(creditCard, HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Customer not found", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Unauthorized access", HttpStatus.UNAUTHORIZED);
    }

    // GET CREDIT CARD BY ID
    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getCreditCardById(@PathVariable String myId) {
        String username = getAuthenticatedUsername();
        User user = userService.findByUsername(username);

        if (isCreditCardOwnedByUser(user, myId)) {
            Optional<CreditCard> creditCard = creditCardService.getById(myId);
            return creditCard.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        return new ResponseEntity<>("Unauthorized access", HttpStatus.FORBIDDEN);
    }

    // UPDATE CREDIT CARD
    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateCreditCard(@PathVariable String myId, @RequestBody CreditCard updatedCreditCard) {
        String username = getAuthenticatedUsername();
        User user = userService.findByUsername(username);

        if (isCreditCardOwnedByUser(user, myId)) {
            CreditCard existingCreditCard = creditCardService.getById(myId).orElse(null);
            if (existingCreditCard != null) {
                if (updatedCreditCard.getCardHolderName() != null) {
                    existingCreditCard.setCardHolderName(updatedCreditCard.getCardHolderName());
                }
                if (updatedCreditCard.getCardNumber() != null) {
                    existingCreditCard.setCardNumber(updatedCreditCard.getCardNumber());
                }
                if (updatedCreditCard.getExpirationDate() != null) {
                    existingCreditCard.setExpirationDate(updatedCreditCard.getExpirationDate());
                }
                creditCardService.saveCreditCard(existingCreditCard);
                return new ResponseEntity<>(existingCreditCard, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Unauthorized access", HttpStatus.FORBIDDEN);
    }

    // DELETE CREDIT CARD
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteCreditCard(@PathVariable String myId) {
        String username = getAuthenticatedUsername();
        User user = userService.findByUsername(username);

        if (isCreditCardOwnedByUser(user, myId)) {
            creditCardService.deleteById(myId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("Unauthorized access", HttpStatus.FORBIDDEN);
    }

    // Utility method to get authenticated username
    private String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    // Check if a credit card belongs to the user's customers
    private boolean isCreditCardOwnedByUser(User user, String creditCardId) {
        if (user != null) {
            return user.getCustomers()
                    .stream()
                    .flatMap(customer -> customer.getCreditCardIds().stream())
                    .anyMatch(id -> id.equals(creditCardId));
        }
        return false;
    }
}
