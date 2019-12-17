package ru.mdorofeev.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.api.model.common.Error;
import ru.mdorofeev.finance.exception.ServiceException;
import ru.mdorofeev.finance.persistence.Session;
import ru.mdorofeev.finance.persistence.User;
import ru.mdorofeev.finance.persistence.dict.SessionStatus;
import ru.mdorofeev.finance.repository.SessionRepository;
import ru.mdorofeev.finance.repository.UserRepository;

import java.io.IOException;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    public User createUser(String login, String password) throws ServiceException, IOException {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            Error error = new Error();
            error.setCode("USER_ALREADY_EXISTS");
            error.setMessage("User already exists");

            throw new ServiceException("USER_ALREADY_EXISTS");
        }

        return userRepository.save(new User(null, login, Utils.md5(password)));
    }

    public User findUser(String login, String password) throws IOException {
        return userRepository.findByLoginAndPassword(login, Utils.md5(password));
    }

    public User findBySession(Long sessionId) {
        return userRepository.findByActiveSession(sessionId);
    }

    public Session createSession(User user) {
        Session session = new Session();
        session.setUser(user);
        session.setStatus(SessionStatus.ACTIVE.id);
        session.setSessionId(Math.abs(UUID.randomUUID().getMostSignificantBits()));

        return sessionRepository.save(session);
    }

    public void deactivateSession(User user) {
        sessionRepository.updateSessions(user, SessionStatus.INACTIVE.id);
    }

}
