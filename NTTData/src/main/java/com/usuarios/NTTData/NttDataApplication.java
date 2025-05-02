package com.usuarios.NTTData;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class NttDataApplication {
	public static void main(String[] args) {
		SpringApplication.run(NttDataApplication.class, args);
	}
}
