package ru.mdorofeev.finance.common.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@JsonInclude(JsonInclude.Include.NON_NULL)

@ToString
@NoArgsConstructor
@Data
public class StringResponse extends Response {

    private String result;

    public StringResponse(String result) {
        this.result = result;
    }

    public StringResponse(ErrorResponse error, String result) {
        super(error);
        this.result = result;
    }
}
