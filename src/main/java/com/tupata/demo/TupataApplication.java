package com.tupata.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TupataApplication {

	public static void main(String[] args) {
		SpringApplication.run(TupataApplication.class, args);
	}

}
