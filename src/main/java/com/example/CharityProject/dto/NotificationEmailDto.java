package com.example.CharityProject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record NotificationEmailDto(
        @NotBlank @Email String destinataire,
        @NotBlank String objet,
        @NotBlank String contenu
) {}
```[cite: 5]

        ---

        ### 4. Classe `RabbitConfig.java`
À placer dans le package `config` :

        ```java
package com.example.CharityProject.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE_EMAIL = "notifications.email";

    @Bean
    public Queue emailQueue() {
        return new Queue(QUEUE_EMAIL, true);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}