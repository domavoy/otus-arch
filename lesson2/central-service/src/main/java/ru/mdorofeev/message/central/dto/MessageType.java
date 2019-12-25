package ru.mdorofeev.message.central.dto;

public enum MessageType {

    //TOODO: P1: Move to email
    SMS("module.input.sms"),
    EMAIL("module.input.email"),
    PUSH("module.input.push");

    private String queueName;

    MessageType(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }
}
