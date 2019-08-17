package com.stone.backend.security;

import java.util.Date;
import java.util.UUID;

import org.springframework.core.env.Environment;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenFactory {
	private Environment environment;
	
	public JwtTokenFactory(Environment environment) {
		super();
		this.environment = environment;
	}
	
	public String generateRefreshToken(String userName, Date issureDate) {
		String refreshToken = Jwts.builder()
				.setSubject(userName)
				.setIssuedAt(issureDate)
				.setId(UUID.randomUUID().toString())
				.setExpiration(new Date(System.currentTimeMillis()+Long.parseLong(environment.getProperty("refreshToken.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
				.compact();
		return refreshToken;
	}

	public String generateAccessToken(String userName, Date issureDate) {
		String token = Jwts.builder()
				.setSubject(userName)
				.setIssuedAt(issureDate)
				.setExpiration(new Date(System.currentTimeMillis()+Long.parseLong(environment.getProperty("token.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
				.compact();
		return token;
	}

}
