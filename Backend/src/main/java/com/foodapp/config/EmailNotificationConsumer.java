package com.foodapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.foodapp.controller.RestaurantServiceController;
import com.foodapp.dtos.EmailNotification;

@Component
public class EmailNotificationConsumer {
	private static final Logger logger = LoggerFactory.getLogger(EmailNotificationConsumer.class);


    @Autowired
    private JavaMailSender javaMailSender;

    @RabbitListener(queues = "email-queue")
    public void processEmailNotification(EmailNotification emailNotification) {
        // Process and send email asynchronously
        sendEmailAsync(emailNotification);
    }

    private void sendEmailAsync(EmailNotification emailNotification) {
    	try {
        // Implement email sending logic using JavaMailSender
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailNotification.getTo()!=null?emailNotification.getTo():"anniejaisinghani25@gmail.com");
        message.setSubject(emailNotification.getSubject());
        message.setText(emailNotification.getMessage());

        javaMailSender.send(message);
    	} catch(MailException e) {
    		logger.debug("Sending mail failed due to some error");
    	}
    }
}
