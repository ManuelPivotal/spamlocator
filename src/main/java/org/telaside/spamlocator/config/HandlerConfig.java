package org.telaside.spamlocator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telaside.spamlocator.handler.NetworkInformationHandler;
import org.telaside.spamlocator.handler.SpamLocatorHandler;

@Configuration
public class HandlerConfig {

	@Bean
	public NetworkInformationHandler networkInformationHandler() {
		return new NetworkInformationHandler();
	}
	
	@Bean
	public SpamLocatorHandler spamLocatorHandler() {
		return new SpamLocatorHandler();
	}
}
