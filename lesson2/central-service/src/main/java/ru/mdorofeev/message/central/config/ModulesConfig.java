package ru.mdorofeev.message.central.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//TODO: NEXT: move to database
@Service
public class  ModulesConfig {

    // SMS module
    @Value("${module.sms-queue.input}")
    private String smsInputQueue;

    @Value("${module.sms-queue.status}")
    private String smsStatusQueue;

    @Value("${module.sms-uuid-prefix}")
    private String smsUuidPrefix;

    // EMAIL  module
    @Value("${module.email-queue.input}")
    private String emailInputQueue;

    @Value("${module.email-queue.status}")
    private String emailStatusQueue;

    @Value("${module.email-uuid-prefix}")
    private String emailUuidPrefix;

    public String getSmsInputQueue() {
        return smsInputQueue;
    }

    public String getSmsStatusQueue() {
        return smsStatusQueue;
    }

    public String getSmsUuidPrefix() {
        return smsUuidPrefix;
    }

    public String getEmailInputQueue() {
        return emailInputQueue;
    }

    public String getEmailStatusQueue() {
        return emailStatusQueue;
    }

    public String getEmailUuidPrefix() {
        return emailUuidPrefix;
    }
}
