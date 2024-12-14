package com.project.creditcardpaymentsystem.service;

import com.project.creditcardpaymentsystem.entity.Customer;
import com.project.creditcardpaymentsystem.entity.User;
import com.project.creditcardpaymentsystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Optional<Customer> getById(String myId) {
        return customerRepository.findById(myId);
    }

    public void deleteById(String myId) {
        customerRepository.deleteById(myId);
    }

    public Customer findByName(String name){
        return customerRepository.findByName(name);
    }
}