package ru.mdorofeev.finance.auth;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mdorofeev.finance.auth.persistence.Session;
import ru.mdorofeev.finance.auth.persistence.User;
import ru.mdorofeev.finance.auth.repository.SessionRepository;
import ru.mdorofeev.finance.auth.repository.UserRepository;
import ru.mdorofeev.finance.auth.service.AuthService;
import ru.mdorofeev.finance.common.exception.ServiceException;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.mdorofeev.finance.auth.service.Utils.md5;

@ActiveProfiles("db-h2mem")
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AuthRepositoryTest {

    @Resource
    private UserRepository userRepository;

    @Resource
    private SessionRepository sessionRepository;

    @Autowired
    private AuthService authService;

    @Test
    public void test() throws IOException, ServiceException {
        authService.createTables();

        User user = authService.createUser("login", "password");
        Assert.assertNotNull(user.getId());
        Assert.assertEquals(user, authService.getUserById(user.getId()));

        User findUser = authService.findUser("login", "password");
        Assert.assertNotNull(findUser);
        Assert.assertEquals("login", findUser.getLogin());
        Assert.assertEquals(md5("password"), findUser.getPassword());

        Session session = authService.createSession(findUser);
        Assert.assertNotNull(session.getId());

        findUser = authService.findBySession(session.getSessionId());
        Assert.assertNotNull(findUser);
        Assert.assertEquals("login", findUser.getLogin());
        Assert.assertEquals(md5("password"), findUser.getPassword());

        authService.deactivateSession(findUser);

        // user not found
        user = authService.getUserById(4343L);
        Assert.assertNull(user);

        user = authService.findUser("login2222", "passwor2222d");
        Assert.assertNull(user);
    }

    @Test(expected = ServiceException.class)
    public void createUserTwice() throws IOException, ServiceException {
        authService.createTables();

        User user = authService.createUser("login22", "password22");
        Assert.assertNotNull(user.getId());
        authService.createUser("login22", "password22");
    }

    @Ignore
    @Test
    public void testOld() {
        List<Long> orderIds = new ArrayList<>(10);

        userRepository.createIfNotExistsTable();
        sessionRepository.createIfNotExistsTable();
        System.out.println("1.Insert--------------");
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setLogin("login");
            user.setPassword("password");
            userRepository.insert(user);
            long orderId = user.getId();
            orderIds.add(orderId);

            Session session = new Session();
            session.setSessionId(100L);
            session.setStatus(100);
            session.setUserId(orderId);

            sessionRepository.insert(session);
        }
    }
}
