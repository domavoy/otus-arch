package ru.mdorofeev.finance.auth.api.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;


/**
 * The Root Schema
 * <p>
 */
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
