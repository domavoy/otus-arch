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
import ru.mdorofeev.message.central.api.dto.StatusResponse;
import ru.mdorofeev.message.central.config.ModulesConfig;
import ru.mdorofeev.message.common.dto.EmailData;
import ru.mdorofeev.message.common.dto.SmsData;
import ru.mdorofeev.message.common.json.ObjectConverter;
import ru.mdorofeev.message.central.api.dto.EmailRequest;
import ru.mdorofeev.message.central.api.dto.Response;

import javax.jms.Message;
import javax.jms.TextMessage;

@Service
public class CentralControllerImpl implements CentralController {

    private static final Logger logger = LoggerFactory.getLogger(CentralControllerImpl.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ModulesConfig config;

    @Value("${module.call-timeout}")
    private int timeOut;

    @Value("${central.input-queue}")
    private String centralInputQueue;


    @Override
    public ResponseEntity<Response> sendEmail(EmailRequest request) {
        try{
            String uuid = Utils.moduleToUuid(config.getEmailUuidPrefix());

            EmailData emailData = new EmailData();
            emailData.setUuid(uuid);
            emailData.setTo(request.getEmail());
            emailData.setTitle(request.getTitle());
            emailData.setMessage(request.getMessage());

            JSONObject json = new ObjectConverter<>().objectToJsonObject(emailData);
            jmsTemplate.convertAndSend(config.getEmailInputQueue(), json.toString());

            return new ResponseEntity<>(new Response(uuid, ""), HttpStatus.OK);
        } catch (Exception e){
            logger.error("Failed to send email due: {}", e.getMessage(), e);
            Response response = new Response();
            response.setErrorMessage("ERROR: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Response> sendSms(SmsRequest request) {
        try{
            String uuid = Utils.moduleToUuid(config.getSmsUuidPrefix());

            SmsData smsData = new SmsData();
            smsData.setUuid(uuid);
            smsData.setPhone(request.getPhone());
            smsData.setMessage(request.getMessage());

            JSONObject json = new ObjectConverter<>().objectToJsonObject(smsData);
            jmsTemplate.convertAndSend(config.getSmsInputQueue(), json.toString());

            return new ResponseEntity<>(new Response(uuid, null), HttpStatus.OK);
        } catch (Exception e){
            logger.error("Failed to send sms due: {}", e.getMessage(), e);

            Response response = new Response();
            response.setErrorMessage("ERROR: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<StatusResponse> getStatus(String uuid) {
        try{
            StatusResponse response = new StatusResponse();
            String moduleQueue = Utils.uuidToModuleStatusQueue(config, uuid);

            jmsTemplate.convertAndSend(moduleQueue, uuid, m -> {
                m.setStringProperty("JMSCorrelationID", uuid);
                m.setStringProperty("REPLY_TO", centralInputQueue);
                return m;
            });

            jmsTemplate.setReceiveTimeout(timeOut);

            Message responseMessage = jmsTemplate.receiveSelected(centralInputQueue, "JMSCorrelationID='" + uuid + "'");
            if (responseMessage == null) {
                throw new ServiceException("Module call timeout exception");
            } else {
                TextMessage text = (TextMessage) responseMessage;
                response.setStatus(text.getText());
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            logger.error("Failed to get status due: {}", e.getMessage(), e);

            StatusResponse response = new StatusResponse();
            response.setErrorMessage("ERROR: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}