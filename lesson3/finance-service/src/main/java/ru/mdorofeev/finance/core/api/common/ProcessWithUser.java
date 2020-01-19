package ru.mdorofeev.finance.core.api.common;

import org.springframework.http.ResponseEntity;

public interface ProcessWithUser<T> {
    ResponseEntity<T> process(Long userId) throws Exception;
}
