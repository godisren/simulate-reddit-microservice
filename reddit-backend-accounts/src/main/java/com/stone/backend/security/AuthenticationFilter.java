package com.stone.backend.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stone.backend.dto.LoginRequestDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private JwtTokenFactory jwtTokenFactory;

	public AuthenticationFilter(JwtTokenFactory jwtTokenFactory, AuthenticationManager authenticationManager) {
		super();
		this.jwtTokenFactory = jwtTokenFactory;
		this.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {

		try {
			LoginRequestDto creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequestDto.class);

			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), new ArrayList()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	// will be called if authenticate success
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		String userName = ((User) auth.getPrincipal()).getUsername();

		Date issureDate = new Date();
		
		String token = jwtTokenFactory.generateAccessToken(userName, issureDate);
		String refreshToken = jwtTokenFactory.generateRefreshToken(userName, issureDate);
		
		res.addHeader("token", token);
		res.addHeader("refresh-token", refreshToken);
		res.addHeader("userName", userName);
	}	

}
