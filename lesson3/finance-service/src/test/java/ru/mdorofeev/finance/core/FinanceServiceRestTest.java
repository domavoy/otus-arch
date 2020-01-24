package ru.mdorofeev.finance.core;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.core.api.ConfigurationController;
import ru.mdorofeev.finance.core.api.MainController;

@Disabled
@ActiveProfiles({"db-h2mem"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FinanceServiceRestTest {

    @Autowired
    private ConfigurationController configurationController;

    @Autowired
    private MainController mainController;

    @Test
    public void fullTest(){
        //TODO
    }
}
