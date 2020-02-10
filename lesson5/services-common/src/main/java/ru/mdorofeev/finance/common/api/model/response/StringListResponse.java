package ru.mdorofeev.finance.common.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


/**
 * The Root Schema
 * <p>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

@ToString
@NoArgsConstructor
@Data
public class StringListResponse extends Response {

    private List<String> result;

    public StringListResponse(ErrorResponse error, List<String> result) {
        super(error);
        this.result = result;
    }
}
