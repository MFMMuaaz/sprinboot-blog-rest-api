package com.springboot.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;

@SpringBootApplication
public class BlogApplication {
	@Bean
	public ModelMapper map() {
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
