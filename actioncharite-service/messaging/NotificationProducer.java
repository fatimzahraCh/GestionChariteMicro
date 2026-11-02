package com.example.actioncharite.services; // Adaptez selon votre package exact

import com.example.actioncharite.messaging.NotificationProducer;
import org.springframework.stereotype.Service;

@Service
public class ActionChariteService {

    private final NotificationProducer notificationProducer;
    // (Ajoutez ici vos autres repositories ou dépendances nécessaires, ex: actionChariteRepository)

    // Injection via le constructeur
    public ActionChariteService(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }

    /**
     * Méthode de création d'une action de charité
     */
    public Object creerActionCharite(/* Vos paramètres, ex: ActionChariteDto dto */) {
        // 1. Logique métier pour enregistrer l'action dans la base de données
        // ex: ActionCharite savedAction = actionChariteRepository.save(...);

        // 2. Envoi de la notification de manière asynchrone via RabbitMQ
        notificationProducer.envoyerNotification(
                "admin@charite.com",
                "Nouvelle action de charité créée",
                "Une nouvelle action de charité a été ajoutée avec succès dans le système."
        );

        // 3. Retourner le résultat
        return "Action créée avec succès et notification envoyée !";
    }
}