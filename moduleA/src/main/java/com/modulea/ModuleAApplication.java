package com.modulea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example", "com.modulea"})
public class ModuleAApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuleAApplication.class, args);
	}

}
