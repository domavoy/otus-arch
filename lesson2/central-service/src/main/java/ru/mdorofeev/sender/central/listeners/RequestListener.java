package ru.mdorofeev.sender.central.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RequestListener {

    @Value("${amq.central.broker.queue.input}")
    private String processRequestQueue;

    private JmsTemplate jmsTemplate;

    @Autowired
    public RequestListener(JmsTemplate jmsTemplate){
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = "${amq.claims.client-cache.response.queue}", containerFactory = "jmsListenerContainerFactoryCC")
    public void onMessage(@Payload String message, @Headers Map<String, Object> headers) {

    }
}
