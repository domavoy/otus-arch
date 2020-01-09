package ru.mdorofeev.finance.core.api.model.response;

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
