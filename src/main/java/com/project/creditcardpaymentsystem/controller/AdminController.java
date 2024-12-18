package com.project.creditcardpaymentsystem.controller;

import com.project.creditcardpaymentsystem.entity.Customer;
import com.project.creditcardpaymentsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CustomerService customerService;

    // Get all customers - Only accessible by admin
    @GetMapping("/all-customers")
    public ResponseEntity<?> getAllCustomers() {
        List<Customer> allCustomers = customerService.findAllCustomers();
        if (allCustomers != null && !allCustomers.isEmpty()) {
            return new ResponseEntity<>(allCustomers, HttpStatus.OK);
        }
        return new ResponseEntity<>("No customers found.", HttpStatus.NOT_FOUND);
    }
}
