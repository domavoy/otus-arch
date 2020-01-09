package ru.mdorofeev.finance.core.api.model.common;

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
public class Session extends Response {

    //TODO: P3: RESEARCH: when sessionId = Long => swagger cut return value
    private String sessionId;

    public Session(Error error) {
        super(error);
    }
}
