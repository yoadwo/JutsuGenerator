package com.gingos.ai.jutsugenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties()
public class JutsuGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(JutsuGeneratorApplication.class, args);
	}
}
