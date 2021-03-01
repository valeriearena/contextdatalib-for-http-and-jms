package com.example.modulea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class ModuleAApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuleAApplication.class, args);
	}

}
