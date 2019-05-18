package org.telaside.spamlocator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.telaside.spamlocator.domain.IPGeoLocation;
import org.telaside.spamlocator.repository.IPGeoLocationRepository;

public class IPGeoLocationService {
	
	@Autowired
	private IPGeoLocationRepository ipGeoLocationRepository;
	
	@Autowired
	private IPGeoLocationRemoteService ipGeoLocationRemoteService;
	
	@Transactional(readOnly = false)
	public IPGeoLocation getLocalOrRemote(String ip) {
		IPGeoLocation local = ipGeoLocationRepository.getByIp(ip);
		if(local != null) {
			return local;
		}
		local = ipGeoLocationRemoteService.getRemoteGeoLocationFor(ip);
		ipGeoLocationRepository.save(local);
		return local;
	}
}
