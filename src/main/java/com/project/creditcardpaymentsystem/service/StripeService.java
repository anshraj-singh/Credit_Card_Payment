package com.project.creditcardpaymentsystem.service;

import com.project.creditcardpaymentsystem.entity.Customer;
import com.project.creditcardpaymentsystem.entity.ProductRequest;
import com.project.creditcardpaymentsystem.entity.StripeResponse;
import com.project.creditcardpaymentsystem.entity.User;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey;

    @Autowired
    private EmailService emailService; // Inject EmailService

    @Autowired
    private UserService userService; // Inject UserService to get customer details

    public StripeResponse checkoutProducts(ProductRequest productRequest) {
        // Set your secret key. Remember to switch to your live secret key in production!
        Stripe.apiKey = secretKey;

        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        // Assuming the first customer is used for the payment
        Customer customer = user.getCustomers().get(0);
        String customerEmail = customer.getEmail(); // Get the customer's email

        // Create product data
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(productRequest.getName())
                        .build();

        // Create price data
        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(productRequest.getCurrency() != null ? productRequest.getCurrency() : "USD")
                        .setUnitAmount(productRequest.getAmount())
                        .setProductData(productData)
                        .build();

        // Create line item
        SessionCreateParams.LineItem lineItem =
                SessionCreateParams.LineItem.builder()
                        .setQuantity(productRequest.getQuantity())
                        .setPriceData(priceData)
                        .build();

        // Create session parameters
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:8080/success")
                        .setCancelUrl("http://localhost:8080/cancel")
                        .addLineItem(lineItem)
                        .build();

        // Create session
        Session session;
        try {
            session = Session.create(params);
            // Send email notification after successful session creation
            sendPaymentNotification(customerEmail, session);
        } catch (StripeException e) {
            // Log the error (you can use a logger here)
            return StripeResponse.builder()
                    .status("ERROR")
                    .message("Failed to create session: " + e.getMessage())
                    .build();
        }

        return StripeResponse.builder()
                .status("SUCCESS")
                .message("Payment session created")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }

    private void sendPaymentNotification(String customerEmail, Session session) {
        String subject = "Payment Successful";
        String body = String.format("Dear Customer,\n\n" +
                        "Your payment has been successfully processed.\n" +
                        "Session ID: %s\n" +
                        "Amount: %d %s\n" +
                        "Thank you for your purchase!\n\n" +
                        "Best regards,\n" +
                        "Your Company Name",
                session.getId(), session.getAmountTotal(), session.getCurrency());

        emailService.sendTransactionNotification(customerEmail, subject, body);
    }
}