package ru.mdorofeev.message.sender.email.listeners;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RequestListener {

    @JmsListener(destination = "${amq.service.queue.input}", containerFactory = "jmsListenerContainerFactory")
    public void onMessage(@Payload String message, @Headers Map<String, Object> headers) {


    }
}
