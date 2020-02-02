package ru.mdorofeev.message.common.json;

import org.json.JSONObject;

import java.io.IOException;

public class ObjectConverter<T> {

    private CustomObjectMapper mapper = new CustomObjectMapper("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public T jsonToObject(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String objectToJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject objectToJsonObject(Object obj) {
        return new JSONObject(objectToJson(obj));
    }
}
