package ru.mdorofeev.finance.common.api.model.response;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Response {

    private ErrorResponse error;

    public Response() {
    }

    public Response(ErrorResponse error) {
        this.error = error;
    }
}
