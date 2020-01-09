package ru.mdorofeev.finance.core.api.common;

import org.springframework.http.ResponseEntity;
import ru.mdorofeev.finance.core.persistence.User;

public interface ProcessWithUser<T> {
    ResponseEntity<T> process(User user) throws Exception;
}
