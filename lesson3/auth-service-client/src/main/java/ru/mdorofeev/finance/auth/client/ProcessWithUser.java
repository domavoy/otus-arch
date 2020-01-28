package ru.mdorofeev.finance.auth.client;

import org.springframework.http.ResponseEntity;

public interface ProcessWithUser<T> {
    ResponseEntity<T> process(Long userId) throws Exception;
}
