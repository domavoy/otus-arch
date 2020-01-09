package ru.mdorofeev.finance.auth.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)

@ToString
@Data
public class AccountStatResponse {

    private String accounName;

    private Double amount;

    private String currency;
}
