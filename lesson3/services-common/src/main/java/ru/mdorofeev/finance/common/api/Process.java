package ru.mdorofeev.finance.common.api;

import org.springframework.http.ResponseEntity;

public interface Process<T> {
    ResponseEntity<T> process() throws Exception;
}
