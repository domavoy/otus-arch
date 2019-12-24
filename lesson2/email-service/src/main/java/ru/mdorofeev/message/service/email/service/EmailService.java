package ru.mdorofeev.message.service.email.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mdorofeev.message.common.dto.EmailData;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendEmail(EmailData emailData){
        logger.info("Send email => " + emailData.getTo() + ", " + emailData.getTitle() + ", " + emailData.getMessage());
    }
}
