package ru.murad.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.murad.dto.EmailRequest;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            logger.info("Email sent successfully to: {}", to);
        } catch (Exception e) {
            logger.error("Failed to send email to {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendEmail(EmailRequest emailRequest) {
        sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getMessage());
    }

    public void sendUserCreatedEmail(String email, String username) {
        String subject = "Добро пожаловать на наш сайт!";
        String message = String.format(
                "Здравствуйте, %s!\n\nВаш аккаунт на сайте был успешно создан.\n\nС уважением,\nКоманда сайта",
                username != null ? username : ""
        );
        sendEmail(email, subject, message);
    }

    public void sendUserDeletedEmail(String email, String username) {
        String subject = "Ваш аккаунт был удален";
        String message = String.format(
                "Здравствуйте%s!\n\nВаш аккаунт был удалён.\n\nС уважением,\nКоманда сайта",
                username != null ? " " + username : ""
        );
        sendEmail(email, subject, message);
    }
}
