package ru.mdorofeev.finance.budget.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.mdorofeev.finance.common.api.model.response.Response;
import ru.mdorofeev.finance.common.api.model.response.ErrorResponse;


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


    public LongResponse(Long result) {
        this.result = result;
    }

    public LongResponse(ErrorResponse error, Long result) {
        super(error);
        this.result = result;
    }
}
