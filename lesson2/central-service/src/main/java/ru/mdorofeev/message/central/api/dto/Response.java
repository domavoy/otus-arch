package ru.mdorofeev.message.central.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Response {

    public Response(String uuid) {
        this.uuid = uuid;
    }

    private String uuid;

    private String errorCode;
}
