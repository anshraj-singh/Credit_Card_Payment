package com.project.creditcardpaymentsystem.repository;

import com.project.creditcardpaymentsystem.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username); // Corrected field name
}
