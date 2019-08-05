package com.stone.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private Environment environment;
	
	@Autowired
	public WebSecurity(Environment environment) {
		super();
		this.environment = environment;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable();
		http.headers().frameOptions().disable();	// for allowing h2 console
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, environment.getProperty("api.registration.url.path")).permitAll()
			.antMatchers(HttpMethod.POST, environment.getProperty("api.login.url.path")).permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilter(new AuthorizationFilter(authenticationManager(), environment));
		
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}
	
	@Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");	//"http://127.0.0.1:3000"
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
	    config.addExposedHeader("token");	// allow token be exposed in response headers
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**",config);
        return new CorsFilter(corsConfigurationSource);
    }

}
