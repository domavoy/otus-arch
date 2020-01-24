package ru.mdorofeev.finance.budget.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

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
