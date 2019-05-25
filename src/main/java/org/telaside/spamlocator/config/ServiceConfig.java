package org.telaside.spamlocator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telaside.spamlocator.service.IPGeoLocationRemoteService;
import org.telaside.spamlocator.service.IPGeoLocationService;
import org.telaside.spamlocator.service.LineInfoExtractor;
import org.telaside.spamlocator.service.SpamLocatorService;

@Configuration
public class ServiceConfig {

	@Bean
	public LineInfoExtractor ipAddressExtractor() {
		return new LineInfoExtractor();
	}
	
	@Bean
	public IPGeoLocationRemoteService ipGeoLocationRemoteService() {
		return new IPGeoLocationRemoteService();
	}
	
	@Bean
	public IPGeoLocationService ipGeoLocationService() {
		return new IPGeoLocationService();
	}
	
	@Bean
	public SpamLocatorService spamLocatorService() {
		return new SpamLocatorService();
	}
}
