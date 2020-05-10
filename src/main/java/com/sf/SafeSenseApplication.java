package com.sf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@ComponentScan
@EnableTransactionManagement
public class SafeSenseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafeSenseApplication.class, args);
	}
}
