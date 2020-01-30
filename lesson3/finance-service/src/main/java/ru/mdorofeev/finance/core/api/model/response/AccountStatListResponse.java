package ru.mdorofeev.finance.core.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import ru.mdorofeev.finance.common.api.model.response.Response;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

@ToString
@Data
public class AccountStatListResponse extends Response {

    private List<AccountStatResponse> value;

    public AccountStatListResponse(List<AccountStatResponse> value) {
        this.value = value;
    }
}
