package org.telaside.spamlocator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.telaside.spamlocator.domain.IPGeoLocation;

public class IPGeoLocationRemoteService {
	
	private static final Logger LOG = LoggerFactory.getLogger(IPGeoLocationRemoteService.class);
	
	@Value("${iptasck-url-template}")
	private String ipstackURLTemplate;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public IPGeoLocation getRemoteGeoLocationFor(String ip) {
		String uri = String.format(ipstackURLTemplate, ip);
		LOG.info("Calling remote service {} to get ip geolocation {}", uri, ip);
	    return restTemplate.getForObject(uri, IPGeoLocation.class);
	}
}
