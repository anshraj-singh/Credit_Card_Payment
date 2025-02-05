package com.project.creditcardpaymentsystem.repository;

import com.project.creditcardpaymentsystem.entity.CardReplacementRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CardReplacementRepository extends MongoRepository<CardReplacementRequest, String> {
    // Additional query methods can be defined here if needed
}