package ru.mdorofeev.finance.auth.api.common;

import org.springframework.http.ResponseEntity;

public interface Process<T> {
    ResponseEntity<T> process() throws Exception;
}
