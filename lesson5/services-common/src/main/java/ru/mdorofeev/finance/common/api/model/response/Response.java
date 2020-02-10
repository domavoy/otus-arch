package ru.mdorofeev.finance.common.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)

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
