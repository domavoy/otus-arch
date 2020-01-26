package ru.mdorofeev.finance.common.api.model.response;

import lombok.Data;
import lombok.ToString;

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
