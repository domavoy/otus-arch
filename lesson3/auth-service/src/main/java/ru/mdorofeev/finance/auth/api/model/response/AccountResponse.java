package ru.mdorofeev.finance.auth.api.model.response;

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
