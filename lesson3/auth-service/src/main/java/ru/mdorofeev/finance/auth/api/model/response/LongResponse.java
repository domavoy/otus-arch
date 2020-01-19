package ru.mdorofeev.finance.auth.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.mdorofeev.finance.auth.api.model.common.Error;
import ru.mdorofeev.finance.auth.api.model.common.Response;


/**
 * The Root Schema
 * <p>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

@ToString
@NoArgsConstructor
@Data
public class LongResponse extends Response {

    private Long result;

    public LongResponse(Error error, Long result) {
        super(error);
        this.result = result;
    }
}
