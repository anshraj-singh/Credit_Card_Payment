package com.project.creditcardpaymentsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CreditCardPaymentSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditCardPaymentSystemApplication.class, args);
	}

}
