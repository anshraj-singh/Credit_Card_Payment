package com.project.creditcardpaymentsystem.repository;

import com.project.creditcardpaymentsystem.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

}