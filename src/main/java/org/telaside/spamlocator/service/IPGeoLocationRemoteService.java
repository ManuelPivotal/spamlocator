package org.telaside.spamlocator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.telaside.spamlocator.domain.IPGeoLocation;
import org.telaside.spamlocator.domain.IPInfoReturnedData;

public class IPGeoLocationRemoteService {
	
	private static final Logger LOG = LoggerFactory.getLogger(IPGeoLocationRemoteService.class);
	
	@Value("${spamlocator.geolocalisation.use-ipinfo:false}")
	private boolean useipinfo;
	
	@Value("${spamlocator.geolocalisation.ipinfo-url-template}")
	private String ipinfoURLTemplate;
	
	@Value("${spamlocator.geolocalisation.iptasck-url-template}")
	private String ipstackURLTemplate;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public IPGeoLocation getRemoteGeoLocationFor(String ip) {
		return useipinfo ? getUsingIPInfo(ip) : getUsingIpStack(ip);
	}
	
	protected IPGeoLocation getUsingIPInfo(String ip) {
		String uri = String.format(ipinfoURLTemplate, ip);
		LOG.info("Calling ipinfo service {} to get ip geolocation {}", uri, ip);
		return restTemplate.getForObject(uri, IPInfoReturnedData.class).buildIPGeoLocation();
	}

	protected IPGeoLocation getUsingIpStack(String ip) {
		String uri = String.format(ipstackURLTemplate, ip);
		LOG.info("Calling ipstack service {} to get ip geolocation {}", uri, ip);
	    return restTemplate.getForObject(uri, IPGeoLocation.class);
	}
}
