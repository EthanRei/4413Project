package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class }, scanBasePackages = {"com.example.demo.repository", "com.exaxmple.demo.controller"} )
public class Project4413Application {

	public static void main(String[] args) {
		SpringApplication.run(Project4413Application.class, args);
	}

}
