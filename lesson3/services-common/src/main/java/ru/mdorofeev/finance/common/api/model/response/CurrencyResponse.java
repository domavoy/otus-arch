package ru.mdorofeev.finance.common.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@JsonInclude(JsonInclude.Include.NON_NULL)

@ToString
@NoArgsConstructor
@Data
public class CurrencyResponse extends Response {

    private Double result;

    public CurrencyResponse(Double result) {
        this.result = result;
    }

    public CurrencyResponse(ErrorResponse error, Double result) {
        super(error);
        this.result = result;
    }
}
