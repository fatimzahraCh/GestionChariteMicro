package com.example.actioncharite.messaging; // Adaptez selon le package exact du producteur

import com.example.actioncharite.config.RabbitConfig;
import com.example.CharityProject.dto.NotificationEmailDto; // Adaptez selon le package du DTO
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void envoyerNotification(String destinataire, String objet, String contenu) {
        NotificationEmailDto dto = new NotificationEmailDto(destinataire, objet, contenu);
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_EMAIL, dto);
    }
}