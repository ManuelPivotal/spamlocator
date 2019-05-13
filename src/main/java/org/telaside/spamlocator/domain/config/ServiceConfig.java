package org.telaside.spamlocator.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telaside.spamlocator.service.IpAddressExtractor;

@Configuration
public class ServiceConfig {

	@Bean
	public IpAddressExtractor ipAddressExtractor() {
		return new IpAddressExtractor();
	}
}
