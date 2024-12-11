package com.project.creditcardpaymentsystem.controller;

import com.project.creditcardpaymentsystem.entity.Customer;
import com.project.creditcardpaymentsystem.entity.User;
import com.project.creditcardpaymentsystem.service.CustomerService;
import com.project.creditcardpaymentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService; // Add UserService to update User

    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        List<Customer> allCustomers = customerService.findAllCustomers();
        if (!allCustomers.isEmpty()) {
            return new ResponseEntity<>(allCustomers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        try {
            // Save the customer first
            customerService.saveCustomer(customer);

            // Now update the associated user with the new customerId
            if (customer.getId() != null) {
                // Assuming the User ID is passed in the request body
                User user = userService.findByUsername(customer.getName()); // or however you identify the user
                if (user != null) {
                    user.setCustomerId(customer.getId());
                    userService.saveUser (user); // Save the updated user
                }
            }

            return new ResponseEntity<>(customer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getCustomerById(@PathVariable String myId) {
        Optional<Customer> customer = customerService.getById(myId);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String myId, @RequestBody Customer updatedCustomer) {
        Customer existingCustomer = customerService.getById(myId).orElse(null);
        if (existingCustomer != null) {
            if (updatedCustomer.getName() != null && !updatedCustomer.getName().isEmpty()) {
                existingCustomer.setName(updatedCustomer.getName());
            }
            if (updatedCustomer.getEmail() != null && !updatedCustomer.getEmail().isEmpty()) {
                existingCustomer.setEmail(updatedCustomer.getEmail());
            }
            if (updatedCustomer.getPhone() != null && !updatedCustomer.getPhone().isEmpty()) {
                existingCustomer.setPhone(updatedCustomer.getPhone());
            }
            if (updatedCustomer.getAddress() != null && !updatedCustomer.getAddress().isEmpty()) {
                existingCustomer.setAddress(updatedCustomer.getAddress());
            }
            customerService.saveCustomer(existingCustomer);
            return new ResponseEntity<>(existingCustomer, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String myId) {
        customerService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}