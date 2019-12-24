package ru.mdorofeev.message.service.email.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.mdorofeev.message.common.dto.EmailData;
import ru.mdorofeev.message.common.json.ObjectConverter;
import ru.mdorofeev.message.service.email.service.EmailService;

import java.util.Map;

@Component
public class RequestListener {

    private static final Logger logger = LoggerFactory.getLogger(RequestListener.class);

    @Autowired
    EmailService emailService;

    @JmsListener(destination = "${amq.service.queue}", containerFactory = "jmsListenerContainerFactory")
    public void onMessage(@Payload String message, @Headers Map<String, Object> headers) {
        try{
            EmailData request = new ObjectConverter<EmailData>().jsonToObject(message, EmailData.class);
            emailService.sendEmail(request);
        } catch (Exception ex) {
            logger.info("Failed to process email request due: {}", message, ex.getMessage());
            throw new RuntimeException("Failed to process email request", ex);
        }
    }
}
