package com.stone.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.stone.backend.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private Environment enviroment;
	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private JwtTokenFactory jwtTokenFactory; 

	@Autowired
	public WebSecurity(Environment enviroment, UserService userService
			, BCryptPasswordEncoder bCryptPasswordEncoder, JwtTokenFactory jwtTokenFactory) {
		super();
		this.enviroment = enviroment;
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.jwtTokenFactory = jwtTokenFactory;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests()
			.antMatchers("/**").permitAll()
			//.hasIpAddress(enviroment.getProperty("gateway.ip"))
			.antMatchers(enviroment.getProperty("refreshToken.url.path")).permitAll() // Token refresh end-point
			.and()
				.addFilter(getAuthenticationFilter());
		
		// for allowing h2 console
		http.headers().frameOptions().disable();
	}
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(jwtTokenFactory, authenticationManager());
		authenticationFilter.setFilterProcessesUrl(enviroment.getProperty("login.url.path"));
		return authenticationFilter;

	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

}
