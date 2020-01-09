package ru.mdorofeev.finance.core.api.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserData {

    private String login;

    private String passsword;
}
