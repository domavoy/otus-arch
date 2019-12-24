package ru.mdorofeev.sender.central.api;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import ru.mdorofeev.message.common.dto.EmailData;
import ru.mdorofeev.message.common.json.ObjectConverter;
import ru.mdorofeev.sender.central.api.dto.EmailRequest;
import ru.mdorofeev.sender.central.api.dto.Response;

import java.util.UUID;

@Service
public class CentralControllerImpl implements CentralController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${amq.queue.module.email}")
    private String emailModuleQueue;

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