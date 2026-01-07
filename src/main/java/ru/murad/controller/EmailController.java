package ru.murad.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.murad.dto.EmailRequest;
import ru.murad.service.EmailService;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            emailService.sendEmail(emailRequest);
            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send email: " + e.getMessage());
        }
    }

    @PostMapping("/send/user-created")
    public ResponseEntity<String> sendUserCreatedEmail(
            @RequestParam String email,
            @RequestParam(required = false) String username) {
        try {
            emailService.sendUserCreatedEmail(email, username);
            return ResponseEntity.ok("User created email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send email: " + e.getMessage());
        }
    }

    @PostMapping("/send/user-deleted")
    public ResponseEntity<String> sendUserDeletedEmail(
            @RequestParam String email,
            @RequestParam(required = false) String username) {
        try {
            emailService.sendUserDeletedEmail(email, username);
            return ResponseEntity.ok("User deleted email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send email: " + e.getMessage());
        }
    }
}
