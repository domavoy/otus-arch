package ru.mdorofeev.finance.api.common;

import org.springframework.http.ResponseEntity;
import ru.mdorofeev.finance.persistence.User;

public interface ProcessWithUser<T> {
    ResponseEntity<T> process(User user) throws Exception;
}
