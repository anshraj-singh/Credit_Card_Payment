package com.project.creditcardpaymentsystem.controller;

import com.project.creditcardpaymentsystem.entity.ProductRequest;
import com.project.creditcardpaymentsystem.entity.StripeResponse;
import com.project.creditcardpaymentsystem.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts(@RequestBody ProductRequest productRequest) {
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
        return new ResponseEntity<>(stripeResponse,HttpStatus.OK);
    }
}