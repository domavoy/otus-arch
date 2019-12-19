package ru.mdorofeev.sender.central;

import org.apache.activemq.broker.BrokerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CentralApplication implements CommandLineRunner {

	public static void main(String[] args){
		SpringApplication.run(CentralApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		BrokerService broker = new BrokerService();
		broker.addConnector("tcp://localhost:61616");
		broker.setPersistent(false);
		broker.start();
	}
}
