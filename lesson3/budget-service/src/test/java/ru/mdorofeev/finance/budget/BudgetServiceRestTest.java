package ru.mdorofeev.finance.budget;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.budget.api.BudgetControllerImpl;

import java.time.LocalDate;
import java.util.Date;

@ActiveProfiles({"db-h2mem"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BudgetServiceRestTest {

    @Autowired
    private BudgetControllerImpl budgetController;

    @Test
    public void budgetTest(){

    }

    // 2010-01-01
    private Date date(String yyyymmdd){
        return new Date(LocalDate.parse(yyyymmdd).toEpochDay());
    }
}
