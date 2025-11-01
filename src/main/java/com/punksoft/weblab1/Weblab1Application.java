package com.punksoft.weblab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Weblab1Application {

	public static void main(String[] args) {
		SpringApplication.run(Weblab1Application.class, args);
	}

	@RestController
	public static class HealthController {

		@GetMapping("/health")
		public String check() {
			return "ok";
		}
	}
}