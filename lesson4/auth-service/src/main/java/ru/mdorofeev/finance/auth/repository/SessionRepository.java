package ru.mdorofeev.finance.auth.repository;

import org.apache.ibatis.annotations.Mapper;
import ru.mdorofeev.finance.auth.persistence.Session;

@Mapper
public interface SessionRepository {

    void createIfNotExistsTable();

    Long insert(Session model);

    void updateSessions(Long userId, Integer newStatus);
}
