package com.apptrove.ledgerlyBackend.security.util;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.apptrove.ledgerlyBackend.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

@Component
public class JwtUtil {

	private final JwtSecretManager jwtSecretManager;
	
	public JwtUtil(JwtSecretManager jwtSecretManager) {
		super();
		this.jwtSecretManager = jwtSecretManager;
	}

	@Value("${jwt-expiry}")
	private Long jwtExpiryTime;
	
	public String generateToken(Authentication authentication,HttpServletResponse httpServletResponse) {
		Map<String, Object> claims = new HashMap<String, Object>();
		User user = (User) authentication.getPrincipal();
		claims.put("username",user.getUsername());
		claims.put("email",user.getEmailId());
		claims.put("roles",user.getRoles()
				.stream().map(role -> role.getRoleName().toString())
				.collect(Collectors.toList())
				);
		return createToken(claims,user.getUsername());
	}

	@SuppressWarnings("deprecation")
	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+jwtExpiryTime))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	private Key getSignInKey() {
		byte[] decodeKey = Base64.getDecoder().decode(jwtSecretManager.getJwtSecret());
		return Keys.hmacShaKeyFor(decodeKey);
	}
	
	public String extractUsername(String token) {
		return extractClaims(token,Claims::getSubject);
	}
	
	private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	@SuppressWarnings("deprecation")
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public boolean validateToken(String token,User user) {
		final String username = extractUsername(token);
		return (username.equals(user.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractClaims(token , Claims::getExpiration).before(new Date());
	}
	
}
