package ru.mdorofeev.finance.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.mdorofeev.finance.api.model.common.Response;

import java.util.List;


/**
 * The Root Schema
 * <p>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

@ToString
@NoArgsConstructor
@Data
public class AccountListResponse extends Response {

    private List<AccountResponse> result;

    public AccountListResponse(List<AccountResponse> accountResponseList) {
        super(null);
        this.result = accountResponseList;
    }

}
