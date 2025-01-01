package com.project.creditcardpaymentsystem.controller;

import com.project.creditcardpaymentsystem.entity.Customer;
import com.project.creditcardpaymentsystem.entity.User;
import com.project.creditcardpaymentsystem.service.CustomerService;
import com.project.creditcardpaymentsystem.service.ReportService;
import com.project.creditcardpaymentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService; // Inject UserService

    @Autowired
    private ReportService reportService;

    // Get all customers - Only accessible by admin
    @GetMapping("/all-customers")
    public ResponseEntity<?> getAllCustomers() {
        List<Customer> allCustomers = customerService.findAllCustomers();
        if (allCustomers != null && !allCustomers.isEmpty()) {
            return new ResponseEntity<>(allCustomers, HttpStatus.OK);
        }
        return new ResponseEntity<>("No customers found.", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/reports/monthly")
    public ResponseEntity<?> generateMonthlyReport(@RequestParam String customerId, @RequestParam int month, @RequestParam int year) {
        try {
            // Get the currently authenticated user's username
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            // Find the user by username
            User user = userService.findByUsername(userName);
            if (user == null) {
                return new ResponseEntity<>("User  not found.", HttpStatus.NOT_FOUND);
            }

            // Call the report service to generate the report
            reportService.generateMonthlyReport(user.getId(), customerId, month, year);
            return new ResponseEntity<>("Monthly report generated and sent to email.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error generating report: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
