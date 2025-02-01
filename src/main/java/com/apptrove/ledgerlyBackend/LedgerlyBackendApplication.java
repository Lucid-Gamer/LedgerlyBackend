package com.apptrove.ledgerlyBackend;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:commonmessage.properties")
@PropertySource("classpath:commonerrorcode.properties")
public class LedgerlyBackendApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(LedgerlyBackendApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
