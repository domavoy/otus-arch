package ru.mdorofeev.finance.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.mdorofeev.finance.auth.service.AuthService;

@SpringBootApplication
public class AuthApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Autowired
    private AuthService authService;

    @Override
    public void run(String... args) throws Exception {
        authService.createTables();
    }
}