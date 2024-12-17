package com.project.creditcardpaymentsystem.service;

import com.project.creditcardpaymentsystem.entity.Customer;
import com.project.creditcardpaymentsystem.entity.User;
import com.project.creditcardpaymentsystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserService userService;

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public void saveNewCustomer(Customer customer, String userName) {
        User user = userService.findByUsername(userName);
        Customer saved = customerRepository.save(customer);

        // Add new customer to the user
        user.getCustomers().add(saved);
        userService.saveNewUser(user);

        // Update SecurityContext with new user details
        updateUserDetailsInSecurityContext(user);
    }

    // Helper method to update user details in SecurityContext
    private void updateUserDetailsInSecurityContext(User updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUser, authentication.getCredentials(), authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Optional<Customer> getById(String myId) {
        return customerRepository.findById(myId);
    }

    public void deleteById(String myId, String userName) {
        User user = userService.findByUsername(userName);
        boolean removed = user.getCustomers().removeIf(customer -> customer.getId().equals(myId));
        if(removed){
            userService.saveNewUser(user);
            customerRepository.deleteById(myId);
        }
    }
}