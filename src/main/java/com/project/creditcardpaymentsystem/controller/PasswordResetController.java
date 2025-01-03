package com.project.creditcardpaymentsystem.controller;

import com.project.creditcardpaymentsystem.entity.Customer;
import com.project.creditcardpaymentsystem.entity.User;
import com.project.creditcardpaymentsystem.service.CustomerService;
import com.project.creditcardpaymentsystem.service.EmailService;
import com.project.creditcardpaymentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
public class PasswordResetController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CustomerService customerService; // Inject CustomerService

    // Request password reset
    @PostMapping("/reset-request")
    public ResponseEntity<?> requestPasswordReset(@RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user != null && !user.getCustomers().isEmpty()) {
            // Assuming we want to send the reset link to the first customer's email
            Customer customer = user.getCustomers().get(0); // Get the first customer
            String token = userService.generatePasswordResetToken(username);
            emailService.sendPasswordResetEmail(customer.getEmail(), token);
            return new ResponseEntity<>("Password reset link sent to your email.", HttpStatus.OK);
        }
        return new ResponseEntity<>("User  or associated customer not found.", HttpStatus.NOT_FOUND);
    }

    // Reset password
    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        if (userService.validateResetToken(token)) {
            userService.resetPassword(token, newPassword);
            return new ResponseEntity<>("Password has been reset successfully.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid or expired token.", HttpStatus.BAD_REQUEST);
    }
}