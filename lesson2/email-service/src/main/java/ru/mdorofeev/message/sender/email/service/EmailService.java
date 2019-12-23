package ru.mdorofeev.message.sender.email.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mdorofeev.message.common.dto.EmailData;
import ru.mdorofeev.message.sender.email.listeners.RequestListener;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendEmail(EmailData emailData){
        logger.info("Send email => " + emailData.getTitle() + "");
    }
}
