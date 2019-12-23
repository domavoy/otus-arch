package ru.mdorofeev.message.common.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));

        this.setDateFormat(dateFormat);
    }

    public CustomObjectMapper(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));

        this.setDateFormat(dateFormat);
    }

}
