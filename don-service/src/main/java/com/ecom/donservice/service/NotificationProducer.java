package com.ecom.donservice.service;

import com.ecom.donservice.config.RabbitConfig;
import com.ecom.donservice.dto.NotificationEmailDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void envoyerNotification(NotificationEmailDto notification) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NOTIFICATIONS_EMAIL, notification);
    }
}
