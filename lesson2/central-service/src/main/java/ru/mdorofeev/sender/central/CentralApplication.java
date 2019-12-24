package ru.mdorofeev.sender.central;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@SpringBootApplication
public class CentralApplication {

	public static void main(String[] args){
		SpringApplication.run(CentralApplication.class, args);
	}
}
