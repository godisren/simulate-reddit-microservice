package com.stone.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication()
@EnableMongoAuditing
public class RedditWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditWorkerApplication.class, args);
	}
	
	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean() {
	    return new LocalValidatorFactoryBean();
	}

	@Bean
	public ValidatingMongoEventListener validatingMongoEventListener(LocalValidatorFactoryBean lfb) {
	    return new ValidatingMongoEventListener(lfb);
	}
	
}
