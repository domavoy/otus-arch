package ru.mdorofeev.finance.auth.api.common;

import org.springframework.http.ResponseEntity;
import ru.mdorofeev.finance.auth.persistence.User;

public interface ProcessWithUser<T> {
    ResponseEntity<T> process(User user) throws Exception;
}
