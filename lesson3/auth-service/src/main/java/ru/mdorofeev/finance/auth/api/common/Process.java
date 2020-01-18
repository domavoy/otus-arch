package ru.mdorofeev.finance.auth.api.common;

import org.springframework.http.ResponseEntity;

//TODO: P2: REFACTORING: move Response for REST common
public interface Process<T> {
    ResponseEntity<T> process() throws Exception;
}
