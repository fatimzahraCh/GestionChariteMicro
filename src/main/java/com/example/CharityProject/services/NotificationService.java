package com.example.notification.services; // Adaptez selon votre package dans notification-service

import com.example.notification.dto.NotificationEmailDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void envoyerEmail(NotificationEmailDto dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@charite.com");
        message.setTo(dto.destinataire());
        message.setSubject(dto.objet());
        message.setText(dto.contenu());
        mailSender.send(message);
    }
}