package com.apptrove.ledgerlyBackend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:commonmessage.properties")
@PropertySource("classpath:commonerrorcode.properties")
public class LedgerlyBackendApplication {

	private static final Logger logger = LogManager.getLogger(LedgerlyBackendApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(LedgerlyBackendApplication.class, args);
	}

}
