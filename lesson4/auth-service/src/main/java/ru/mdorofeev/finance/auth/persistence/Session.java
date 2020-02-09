package ru.mdorofeev.finance.auth.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public final class Session {

    public Session(Long userId, Long sessionId, Integer status) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.status = status;
    }

    private Long id;

    private Long userId;

    private Long sessionId;

    private Integer status;
}
