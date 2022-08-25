package com.mirceanealcos.confruntarea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class ConfruntareaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfruntareaApplication.class, args);
	}


	/**
	 * Adds a password encoder bean to the Spring Framework
	 */
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
