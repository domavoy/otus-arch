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
public class TransactionRequestInternal {

    private Long userId;

    private Long categoryId;

    private Long accountId;

    //TODO: P2: pass BigDecimal ?
    private Double money;

    private String comment;

}
