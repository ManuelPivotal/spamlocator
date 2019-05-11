package org.telaside.spamlocator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
public class SpamlocatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpamlocatorApplication.class, args);
	}

}
