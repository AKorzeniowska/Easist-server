package com.edu.agh.easist.easistserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

@SpringBootApplication
public class EasistServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasistServerApplication.class, args);
	}
}
