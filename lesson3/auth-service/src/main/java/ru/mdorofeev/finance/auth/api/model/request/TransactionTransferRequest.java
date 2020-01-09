package ru.mdorofeev.finance.auth.api.model.request;

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
public class TransactionTransferRequest {

    private Long sessionId;

    private String fromAccount;

    private String toAccount;

    private Double money;

    private String comment;

}
