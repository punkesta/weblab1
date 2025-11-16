package com.noname.weblabs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SpringBootApplication
public class WeblabsApplication {
	public static void main(String[] args) {
		SpringApplication.run(WeblabsApplication.class, args);
	}

	@Bean
	public Gson gson() {
		return new GsonBuilder().setPrettyPrinting().create();
	}
}