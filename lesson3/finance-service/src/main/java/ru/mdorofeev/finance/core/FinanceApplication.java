package ru.mdorofeev.finance.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.mdorofeev.finance.core.service.PredefinedDataLoader;

@SpringBootApplication
public class FinanceApplication implements CommandLineRunner {

    public static final String DATA_LOAD_PROFILE = "h2mem";
    @Value("${spring.profiles.active}")
    private String activeProfile;
    @Autowired
    private PredefinedDataLoader predefinedDataLoader;

    public static void main(String[] args) {
        SpringApplication.run(FinanceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        //for h2mem profile => load predefined data
        if (DATA_LOAD_PROFILE.equals(activeProfile)) {
            predefinedDataLoader.loadData("login", "password", "moneyPro.csv");
        }
    }
}