package com.project.creditcardpaymentsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FAQService {

    @Autowired
    private GeminiIntegrationService geminiIntegrationService;

    // Predefined questions related to credit cards
    private static final Map<String, String> FAQ_MAP = new HashMap<>();

    static {
        FAQ_MAP.put("What is a credit card?", "A credit card is a payment card issued to users to enable the cardholder to pay a merchant for goods and services.");
        FAQ_MAP.put("How do I apply for a credit card?", "You can apply for a credit card online, through a bank branch, or by calling the bank's customer service.");
        FAQ_MAP.put("What are the benefits of using a credit card?", "Credit cards offer rewards, cash back, and the ability to build credit history.");
        FAQ_MAP.put("What is the interest rate on credit cards?", "Interest rates vary by card and issuer, but they are typically expressed as an Annual Percentage Rate (APR).");
        FAQ_MAP.put("How can I avoid credit card debt?", "To avoid credit card debt, pay your balance in full each month and avoid unnecessary purchases.");
        FAQ_MAP.put("How to register and login?", "You can register by providing your personal information and creating a password. To login, enter your registered email and password.");
        FAQ_MAP.put("How does a credit card work?", "A credit card allows you to borrow money from a bank to make purchases, which you must pay back with interest if not paid in full by the due date.");
        FAQ_MAP.put("What should I do if my credit card is lost or stolen?", "Immediately report the loss or theft to your bank to prevent unauthorized transactions.");
        FAQ_MAP.put("How can I increase my credit limit?", "You can request a credit limit increase from your bank, which may require a review of your credit history and income.");
        FAQ_MAP.put("What are credit card rewards?", "Credit card rewards are benefits offered by credit card issuers, such as cash back, points, or travel miles for purchases made with the card.");
        FAQ_MAP.put("What is a credit score?", "A credit score is a numerical representation of your creditworthiness, based on your credit history.");
        FAQ_MAP.put("How can I improve my credit score?", "You can improve your credit score by paying bills on time, reducing debt, and avoiding new hard inquiries.");
        FAQ_MAP.put("What is a secured credit card?", "A secured credit card is backed by a cash deposit that serves as your credit limit, making it easier to obtain for those with poor credit.");
        FAQ_MAP.put("What is a grace period?", "A grace period is the time during which you can pay your credit card bill without incurring interest on new purchases.");
        FAQ_MAP.put("What happens if I miss a payment?", "Missing a payment can result in late fees, increased interest rates, and a negative impact on your credit score.");
        FAQ_MAP.put("Can I use my credit card internationally?", "Yes, most credit cards can be used internationally, but you may incur foreign transaction fees.");
        FAQ_MAP.put("What is a cash advance?", "A cash advance allows you to withdraw cash from your credit card, usually at a higher interest rate and with fees.");
        FAQ_MAP.put("How do I dispute a charge?", "To dispute a charge, contact your credit card issuer and provide details about the transaction you believe is incorrect.");
        FAQ_MAP.put("What is a balance transfer?", "A balance transfer allows you to move debt from one credit card to another, often to take advantage of lower interest rates.");
        FAQ_MAP.put("What are the fees associated with credit cards?", "Common fees include annual fees, late payment fees, foreign transaction fees, and cash advance fees.");
        FAQ_MAP.put("How can I set up alerts for my credit card?", "You can set up alerts through your bank's mobile app or website to receive notifications for due dates, spending limits, and more.");
        FAQ_MAP.put("What is the difference between a credit card and a debit card?", "A credit card allows you to borrow money up to a limit, while a debit card withdraws funds directly from your bank account.");
        FAQ_MAP.put("How do I close a credit card account?", "To close a credit card account, contact your issuer and request to close the account, ensuring any balance is paid off first.");
        FAQ_MAP.put("What is a credit card statement?", "A credit card statement is a monthly summary of your transactions, payments, and balance on your credit card.");
        FAQ_MAP.put("How can I avoid foreign transaction fees?", "To avoid foreign transaction fees, use a credit card that does not charge these fees or use local currency when possible.");
        FAQ_MAP.put("What is a promotional APR?", "A promotional APR is a temporary lower interest rate offered for a specific period, often for balance transfers or new purchases.");
        FAQ_MAP.put("How do I report fraud on my credit card?", "Report fraud immediately to your credit card issuer to dispute unauthorized charges and protect your account.");
        FAQ_MAP.put("What is a minimum payment?", "The minimum payment is the smallest amount you can pay on your credit card bill to keep your account in good standing.");
        FAQ_MAP.put("Can I have multiple credit cards?", "Yes, you can have multiple credit cards, but it's important to manage them responsibly to avoid debt.");
        FAQ_MAP.put("What is a credit card issuer?", "A credit card issuer is a financial institution that provides credit cards to consumers and manages their accounts.");
        FAQ_MAP.put("How do I choose the right credit card for me?", "To choose the right credit card, consider factors such as rewards, interest rates, fees, and your spending habits.");
        FAQ_MAP.put("What is a contactless credit card?", "A contactless credit card allows you to make payments by tapping the card on a compatible terminal, providing a quick and convenient way to pay.");
        FAQ_MAP.put("What should I do if my credit card is declined?", "If your credit card is declined, check for any issues such as exceeding your credit limit, expired card, or potential fraud alerts, and contact your issuer for assistance.");
        FAQ_MAP.put("How can I track my credit card spending?", "You can track your credit card spending through your bank's mobile app, online banking, or by reviewing your monthly statements.");
        FAQ_MAP.put("What is a credit card fraud alert?", "A credit card fraud alert is a notification from your issuer that suspicious activity has been detected on your account, prompting you to verify recent transactions.");
        FAQ_MAP.put("How do I manage credit card debt?", "To manage credit card debt, create a budget, prioritize payments, and consider consolidating debt or seeking financial advice if needed.");
    }

    public String getFAQResponse(String question) {
        // Check if the question is in the predefined list
        if (FAQ_MAP.containsKey(question)) {
            return FAQ_MAP.get(question);
        } else {
            return "Sorry, I don't have an answer for that question.";
        }
    }
}