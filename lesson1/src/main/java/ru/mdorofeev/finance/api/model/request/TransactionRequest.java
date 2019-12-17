package ru.mdorofeev.finance.api.model.request;

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
public class TransactionRequest {

    private Long sessionId;

    private String categoryName;

    private String accountName;

    //TODO: P2: pass BigDecimal ?
    private Double money;

    private String comment;

}
