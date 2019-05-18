package org.telaside.spamlocator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telaside.spamlocator.service.IPGeoLocationRemoteService;
import org.telaside.spamlocator.service.IPGeoLocationService;
import org.telaside.spamlocator.service.IpAddressExtractor;

@Configuration
public class ServiceConfig {

	@Bean
	public IpAddressExtractor ipAddressExtractor() {
		return new IpAddressExtractor();
	}
	
	@Bean
	public IPGeoLocationRemoteService ipGeoLocationRemoteService() {
		return new IPGeoLocationRemoteService();
	}
	
	@Bean
	public IPGeoLocationService ipGeoLocationService() {
		return new IPGeoLocationService();
	}
}
