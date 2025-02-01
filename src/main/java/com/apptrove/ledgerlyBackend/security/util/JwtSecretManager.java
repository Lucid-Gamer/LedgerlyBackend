package com.apptrove.ledgerlyBackend.security.util;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Component;

//import jakarta.annotation.PostConstruct;
import lombok.Data;

@Component
@Data 
public class JwtSecretManager {

	private String jwtSecret;
	
//	@PostConstruct
	private void generateSecret() {
		
		SecureRandom secureRandom = new SecureRandom();
		
		byte[] secret = new byte[256];
		
		secureRandom.nextBytes(secret);
		
		this.jwtSecret = Base64.getEncoder().encodeToString(secret);
		
		if(this.jwtSecret.length() > 256) {
			this.jwtSecret = this.jwtSecret.substring(0, 256);
		}
		
		System.out.println("Jwt Secret generated: "+this.jwtSecret);
	}
	
}
