package ru.murad.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.murad.dto.UserEvent;

@Service
public class KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    public KafkaConsumerService(EmailService emailService, ObjectMapper objectMapper) {
        this.emailService = emailService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${kafka.topics.user-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeUserEvent(String message) {
        try {
            logger.info("Received message from Kafka: {}", message);

            UserEvent userEvent = objectMapper.readValue(message, UserEvent.class);
            logger.info("Parsed user event: {}", userEvent);

            switch (userEvent.getEventType()) {
                case USER_CREATED:
                    emailService.sendUserCreatedEmail(userEvent.getEmail(), userEvent.getUsername());
                    break;
                case USER_DELETED:
                    emailService.sendUserDeletedEmail(userEvent.getEmail(), userEvent.getUsername());
                    break;
                default:
                    logger.warn("Unknown event type: {}", userEvent.getEventType());
            }
        } catch (Exception e) {
            logger.error("Error processing Kafka message: {}", e.getMessage(), e);
        }
    }
}
