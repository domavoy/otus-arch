package ru.mdorofeev.finance.auth.client.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;


@JsonInclude(JsonInclude.Include.NON_NULL)

@ToString
@Data
public class Response {

    private Error error;

    public Response() {
    }

    public Response(Error error) {
        this.error = error;
    }
}
