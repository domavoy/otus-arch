package ru.mdorofeev.finance.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.mdorofeev.finance.api.model.common.Response;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

@ToString
@NoArgsConstructor
@Data
public class TransactionListResponse extends Response {

    private List<TransactionResponse> result;

    public TransactionListResponse(List<TransactionResponse> result) {
        this.result = result;
    }
}
