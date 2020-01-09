package ru.mdorofeev.finance.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionResponse {

    private Date date;

    private String transactionType;

    private String categoryName;

    private String accountName;

    private String toAccount;

    //TODO: P2: pass BigDecimal ?
    private Double money;

    private String comment;

}
