package ru.mdorofeev.message.service.sms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mdorofeev.message.common.dto.EmailData;
import ru.mdorofeev.message.common.dto.SmsData;

@Service
public class SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    public void sendSms(SmsData smsData){
        logger.info("Send sms => " + smsData.getPhone() + ", " + smsData.getMessage());
    }
}
