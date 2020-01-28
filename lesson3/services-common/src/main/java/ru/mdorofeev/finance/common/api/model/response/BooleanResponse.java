package ru.mdorofeev.finance.common.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * The Root Schema
 * <p>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

@ToString
@NoArgsConstructor
@Data
public class BooleanResponse extends Response {

    private Boolean result;

    public BooleanResponse(ErrorResponse error, Boolean result) {
        super(error);
        this.result = result;
    }
}
