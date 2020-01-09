package ru.mdorofeev.finance.auth.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.mdorofeev.finance.auth.api.model.common.Response;
import ru.mdorofeev.finance.auth.api.model.common.Error;


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

    public BooleanResponse(Error error, Boolean result) {
        super(error);
        this.result = result;
    }
}
