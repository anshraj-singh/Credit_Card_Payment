package com.project.creditcardpaymentsystem.controller;

import com.project.creditcardpaymentsystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/monthly")
    public ResponseEntity<?> generateMonthlyReport(@RequestParam String userId, @RequestParam String customerId, @RequestParam int month, @RequestParam int year) {
        try {
            reportService.generateMonthlyReport(userId, customerId, month, year);
            return new ResponseEntity<>("Monthly report generated and sent to email.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error generating report: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}