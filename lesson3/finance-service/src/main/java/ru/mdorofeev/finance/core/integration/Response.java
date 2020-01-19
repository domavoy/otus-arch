package ru.mdorofeev.finance.core.integration;

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
//TODO: P2: REFACTORING: move Response for REST common
public class Response {

    private Error error;

    public Response() {
    }

    public Response(Error error) {
        this.error = error;
    }
}
