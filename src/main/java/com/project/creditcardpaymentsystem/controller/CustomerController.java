package com.project.creditcardpaymentsystem.controller;

import com.project.creditcardpaymentsystem.entity.Customer;
import com.project.creditcardpaymentsystem.entity.User;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService; // Add UserService to update User

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer myCustomer) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            customerService.saveNewCustomer(myCustomer,userName);
            User updatedUser = userService.findByUsername(userName);
            return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUsername(userName);

        Optional<Customer> customer = customerService.getById(myId);
        if (customer.isPresent() && user.getCustomers().contains(customer.get())) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String myId, @RequestBody Customer updatedCustomer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUsername(userName);

        Optional<Customer> customerOpt = customerService.getById(myId);
        if (customerOpt.isPresent() && user.getCustomers().contains(customerOpt.get())) {
            Customer existingCustomer = customerOpt.get();
            if (updatedCustomer.getName() != null) {
                existingCustomer.setName(updatedCustomer.getName());
            }
            if (updatedCustomer.getEmail() != null) {
                existingCustomer.setEmail(updatedCustomer.getEmail());
            }
            if (updatedCustomer.getPhone() != null) {
                existingCustomer.setPhone(updatedCustomer.getPhone());
            }
            if (updatedCustomer.getAddress() != null) {
                existingCustomer.setAddress(updatedCustomer.getAddress());
            }
            customerService.saveCustomer(existingCustomer);
            return new ResponseEntity<>(existingCustomer, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        customerService.deleteById(myId,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}