package ru.murad.it;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import ru.murad.service.EmailService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class EmailServiceIntegrationTest {

    @Autowired
    private EmailService emailService;

    @MockBean
    private JavaMailSender mailSender;

    @Test
    void testSendUserCreatedEmail() {
        String email = "test@example.com";
        String username = "Test User";

        emailService.sendUserCreatedEmail(email, username);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendUserDeletedEmail() {
        String email = "test@example.com";
        String username = "Test User";

        emailService.sendUserDeletedEmail(email, username);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
