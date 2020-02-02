package ru.mdorofeev.message.service.sms.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.mdorofeev.message.common.dto.SmsData;
import ru.mdorofeev.message.common.exceptions.ModuleProcessException;
import ru.mdorofeev.message.common.json.ObjectConverter;
import ru.mdorofeev.message.service.sms.service.SmsService;

import java.util.Map;
import java.util.Random;

@Component
public class RequestListener {

    private static final Logger logger = LoggerFactory.getLogger(RequestListener.class);

    @Autowired
    SmsService smsService;

    @Autowired
    private JmsTemplate jmsTemplate;

    public static final String REPLY_TO_HEADER = "REPLY_TO";

    @JmsListener(destination = "${amq.service.input.queue}", containerFactory = "jmsListenerContainerFactory")
    public void onMessage(@Payload String message, @Headers Map<String, Object> headers) {
        try{
            SmsData request = new ObjectConverter<SmsData>().jsonToObject(message, SmsData.class);
            smsService.sendSms(request);
        } catch (Exception ex) {
            logger.info("Failed to process sms request due: {}", message);
            throw new ModuleProcessException("Failed to process sms request", ex);
        }
    }

    @JmsListener(destination = "${amq.service.status.queue}", containerFactory = "jmsListenerContainerFactory")
    public void onStatusMessage(@Payload String uuid, @Headers Map<String, Object> headers) {
        try{
            // calculate reply to
            if(headers.get(REPLY_TO_HEADER) == null){
                throw new ModuleProcessException("Failed to process status message without REPLY_TO header");
            }
            String replyTo = headers.get(REPLY_TO_HEADER).toString();

            // get fake request status
            String status = getRequestStatus(uuid);
            logger.info("RETURN SMS STATUS: " + status);

            // return response to reply_to
            jmsTemplate.convertAndSend(replyTo, status, m -> {
                m.setStringProperty("JMSCorrelationID", uuid);
                return m;
            });
        } catch (Exception ex) {
            logger.info("Failed to process email status request: ", uuid, ex.getMessage());
            throw new RuntimeException("Failed to process email status request", ex);
        }
    }

    private String getRequestStatus(String uuid){
        // module check status logic ...
        // for testing => return DONE status

        String[] statuses = {"DONE"};
        int idx = new Random().nextInt(statuses.length);
        return (statuses[idx]);
    }
}
