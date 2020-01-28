package ru.mdorofeev.finance.repeated;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"ru.mdorofeev.finance.auth.client", "ru.mdorofeev.finance.repeated"})
@SpringBootApplication
public class RepeatedPaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepeatedPaymentApplication.class, args);
    }
}