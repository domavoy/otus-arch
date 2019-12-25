package ru.mdorofeev.message.central.api;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import ru.mdorofeev.message.central.api.dto.SmsRequest;
import ru.mdorofeev.message.common.dto.EmailData;
import ru.mdorofeev.message.common.dto.SmsData;
import ru.mdorofeev.message.common.json.ObjectConverter;
import ru.mdorofeev.message.central.api.dto.EmailRequest;
import ru.mdorofeev.message.central.api.dto.Response;

import java.util.UUID;

@Service
public class CentralControllerImpl implements CentralController {

    private static final Logger logger = LoggerFactory.getLogger(CentralControllerImpl.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${amq.queue.module.email}")
    private String emailModuleQueue;

    @Value("${amq.queue.module.sms}")
    private String smsModuleQueue;

    @Override
    public ResponseEntity<Response> sendEmail(EmailRequest request) {
        try{
            UUID uuid = UUID.randomUUID();

            EmailData emailData = new EmailData();
            emailData.setUuid(uuid);
            emailData.setTo(request.getEmail());
            emailData.setTitle(request.getTitle());
            emailData.setMessage(request.getMessage());

            JSONObject json = new ObjectConverter<>().objectToJsonObject(emailData);
            jmsTemplate.convertAndSend(emailModuleQueue, json.toString());

            return new ResponseEntity<>(new Response(uuid.toString()), HttpStatus.OK);
        } catch (Exception e){
            logger.error("Failed to send email due: {}", e.getMessage(), e);
            Response response = new Response();
            response.setErrorCode("ERROR: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> sendSms(SmsRequest request) {
        try{
            UUID uuid = UUID.randomUUID();

            SmsData smsData = new SmsData();
            smsData.setUuid(uuid);
            smsData.setPhone(request.getPhone());
            smsData.setMessage(request.getMessage());

            JSONObject json = new ObjectConverter<>().objectToJsonObject(smsData);
            jmsTemplate.convertAndSend(smsModuleQueue, json.toString());

            return new ResponseEntity<>(new Response(uuid.toString()), HttpStatus.OK);
        } catch (Exception e){
            logger.error("Failed to send sms due: {}", e.getMessage(), e);

            Response response = new Response();
            response.setErrorCode("ERROR: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<String> getStatus(String uuid) {
        return new ResponseEntity<String>("IN_PROGRESS", HttpStatus.OK);
    }
}