package ru.mdorofeev.finance.budget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"ru.mdorofeev.finance.auth.client", "ru.mdorofeev.finance.budget"})
@SpringBootApplication
public class BudgetApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetApplication.class, args);
    }
}