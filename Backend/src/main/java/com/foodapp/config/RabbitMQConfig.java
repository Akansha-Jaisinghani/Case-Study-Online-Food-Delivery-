package com.foodapp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange("email-exchange");
    }

    @Bean
    public Queue emailQueue() {
        return new Queue("email-queue");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
    
    @Bean 
    MessageConverter converter(){
//    	ObjectMapper objectMapper = new ObjectMapper();
//    	String jsonPayload = objectMapper.writeValueAsString(emailNotification);
//    	rabbitTemplate.convertAndSend(exchange, routingKey, jsonPayload);
    	return new Jackson2JsonMessageConverter();
    }
    
    
    
    @Bean
    public Binding emailBinding(Queue emailQueue, DirectExchange emailExchange) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with("email.notification");
    }
}
