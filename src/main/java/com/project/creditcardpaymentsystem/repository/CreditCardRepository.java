package com.project.creditcardpaymentsystem.repository;

import com.project.creditcardpaymentsystem.entity.CreditCard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CreditCardRepository extends MongoRepository<CreditCard,String> {

}
