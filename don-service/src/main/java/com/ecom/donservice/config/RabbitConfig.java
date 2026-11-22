package com.ecom.donservice.config;
// adapte selon ton package racine dans don-service

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_NOTIFICATIONS_EMAIL = "notifications.email";

    @Bean
    public Queue notificationsEmailQueue() {
        return new Queue(QUEUE_NOTIFICATIONS_EMAIL, true);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}