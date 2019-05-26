package org.telaside.spamlocator.scheduler;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.telaside.spamlocator.domain.IPGeoLocation;
import org.telaside.spamlocator.service.IPGeoLocationService;
import org.telaside.spamlocator.service.SpamLocatorService;

@Profile("scheduled")
@Configuration("ScheduledTasksConf")
@EnableScheduling
public class ScheduledTasks {
	
	private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);
	
	@Autowired
	private SpamLocatorService spamLocatorService;
	
	@Autowired
	private IPGeoLocationService iPGeoLocationService;
	
	@Scheduled(fixedDelay = 300_000)
	public void findAndSaveUnsetIPGeoLocations() {
		LOG.debug("Getting unset IPs");
		Set<String> unsetIps = spamLocatorService.findUnsetIpGeolocation();
		if(!unsetIps.isEmpty()) {
			LOG.info("Unset IPs are {}", unsetIps);
			unsetIps.forEach(ip -> {
				IPGeoLocation found = iPGeoLocationService.getLocalOrRemote(ip);
				LOG.info("Found {} for {}", found, ip);
			});
		}
	}
}
