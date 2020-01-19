package ru.mdorofeev.finance.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.core.exception.ServiceException;
import ru.mdorofeev.finance.core.integration.AuthIntegrationService;

@Disabled
@ActiveProfiles({"db-h2mem"})
@SpringBootTest
public class IntegrationTest {

    @Autowired
    private AuthIntegrationService authIntegrationService;

    @Test
    public void test() throws ServiceException {
        Long data = authIntegrationService.findBySession(6863507891876809208L);
        Assertions.assertNotNull(data, "getUserBySession");
    }
}
