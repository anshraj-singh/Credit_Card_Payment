package com.project.creditcardpaymentsystem.repository;

import com.project.creditcardpaymentsystem.entity.CreditCard;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface CreditCardRepository extends MongoRepository<CreditCard,String> {
    List<CreditCard> findByExpirationDateBetween(LocalDate startDate, LocalDate endDate);

}
