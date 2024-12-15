package com.project.creditcardpaymentsystem.repository;

import com.project.creditcardpaymentsystem.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}
