package com.project.creditcardpaymentsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendTransactionNotification(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            javaMailSender.send(message);
        } catch (Exception e) {
            // Log the error or handle it as needed
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    // New method to send password reset email
    public void sendPasswordResetEmail(String to, String token) {
        String subject = "Password Reset Request";
        String body = "To reset your password, please click the link below:\n" +
                "http://yourdomain.com/reset-password?token=" + token + "\n" +
                "If you did not request a password reset, please ignore this email.";
        sendTransactionNotification(to, subject, body);
    }
}