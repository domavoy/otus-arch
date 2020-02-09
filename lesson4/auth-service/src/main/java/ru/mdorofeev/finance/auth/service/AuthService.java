package ru.mdorofeev.finance.auth.service;

import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.auth.persistence.Session;
import ru.mdorofeev.finance.auth.persistence.User;
import ru.mdorofeev.finance.auth.persistence.dict.SessionStatus;
import ru.mdorofeev.finance.auth.repository.SessionRepository;
import ru.mdorofeev.finance.auth.repository.UserRepository;
import ru.mdorofeev.finance.common.exception.ServiceException;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

@Service
public class AuthService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private SessionRepository sessionRepository;

    //TODO: P2: validate login/password lenght ib db, on error => throw exceptions, add tests
    public User createUser(String login, String password) throws ServiceException, IOException {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            throw new ServiceException("USER_ALREADY_EXISTS");
        }

        User newUser = new User(login, Utils.md5(password));
        userRepository.insert(newUser);
        return newUser;
    }

    public User findUser(String login, String password) throws IOException, ServiceException {
        return userRepository.findByLoginAndPassword(login, Utils.md5(password));
    }

    public User findBySession(Long sessionId) {
        return userRepository.findByActiveSession(sessionId);
    }

    public Session createSession(User user) {
        Session session = new Session();
        session.setUserId(user.getId());
        session.setStatus(SessionStatus.ACTIVE.id);
        session.setSessionId(Math.abs(UUID.randomUUID().getMostSignificantBits()));

        sessionRepository.insert(session);
        return session;
    }

    public void deactivateSession(User user) {
        sessionRepository.updateSessions(user.getId(), SessionStatus.INACTIVE.id);
    }

    public User getUserById(Long userId) throws ServiceException {
        return userRepository.findById(userId);
    }

    public void createTables(){
        userRepository.createIfNotExistsTable();
        sessionRepository.createIfNotExistsTable();
    }
}
