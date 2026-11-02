package com.example.CharityProject.messaging;

import com.example.CharityProject.config.RabbitConfig;
import com.example.CharityProject.dto.NotificationEmailDto;
import com.example.CharityProject.services.NotificationService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {
    private final NotificationService notificationService;
    private final Validator validator;

    public NotificationConsumer(NotificationService notificationService, Validator validator) {
        this.notificationService = notificationService;
        this.validator = validator;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_EMAIL)
    public void recevoir(NotificationEmailDto notification) {
        var erreurs = validator.validate(notification);
        if (!erreurs.isEmpty()) {
            throw new ConstraintViolationException(erreurs);
        }
        notificationService.envoyerEmail(notification);
    }
}