package ru.mdorofeev.finance.core.api.common;

import org.springframework.http.ResponseEntity;

public interface Process<T> {
    ResponseEntity<T> process() throws Exception;
}
