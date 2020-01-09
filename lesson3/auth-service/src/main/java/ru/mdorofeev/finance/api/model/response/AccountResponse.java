package ru.mdorofeev.finance.api.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Data
public class AccountResponse {
    private String name;
    private String currency;
}
