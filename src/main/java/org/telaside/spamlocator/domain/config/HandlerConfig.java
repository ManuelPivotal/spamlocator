package org.telaside.spamlocator.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telaside.spamlocator.handler.NetworkInformationHandler;

@Configuration
public class HandlerConfig {

	@Bean
	public NetworkInformationHandler networkInformationHandler() {
		return new NetworkInformationHandler();
	}
}
