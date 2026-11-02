package com.example.actioncharite.messaging; // Adaptez selon votre package exact

import com.example.actioncharite.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {
    private final RabbitTemplate rabbitTemplate;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void envoyerNotification(String destinataire, String objet, String contenu) {
        // Record ou DTO représentant le message
        NotificationEmailDto dto = new NotificationEmailDto(destinataire, objet, contenu);
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_EMAIL, dto);
    }

    // Définition du DTO interne ou à importer si partagé
    public record NotificationEmailDto(String destinataire, String objet, String contenu) {}
}