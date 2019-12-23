package ru.mdorofeev.message.sender.email.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.mdorofeev.message.common.dto.EmailData;
import ru.mdorofeev.message.common.json.ObjectConverter;

import java.util.Map;

@Component
public class RequestListener {

    private static final Logger logger = LoggerFactory.getLogger(RequestListener.class);

    @JmsListener(destination = "${amq.service.queue.input}", containerFactory = "jmsListenerContainerFactory")
    public void onMessage(@Payload String message, @Headers Map<String, Object> headers) {
        try{
            EmailData request = new ObjectConverter<EmailData>().jsonToObject(message, EmailData.class);

        } catch (Exception ex) {
            logger.info("Receipt ID [ {} ] :: Error processing request: {}", message, ex.getMessage());
            throw new RuntimeException("MessageReceiver.receiveFiscalMsg error", ex);
        }
    }
}
