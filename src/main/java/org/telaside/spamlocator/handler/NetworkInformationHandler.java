package org.telaside.spamlocator.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.telaside.spamlocator.domain.HostHop;
import org.telaside.spamlocator.domain.ReceivedHeader;
import org.telaside.spamlocator.domain.SpamLocatorMessage;
import org.telaside.spamlocator.service.IPGeoLocationService;

public class NetworkInformationHandler {
	
	@Autowired
	private IPGeoLocationService ipGeoLocationService;
	
	public SpamLocatorMessage hostHops(SpamLocatorMessage source) {
		for (ReceivedHeader receivedHeader : source.getReceivedHeaders()) {
			enrichHostHop(receivedHeader.getFrom());
		}
		return source;
	}
	
	private void enrichHostHop(HostHop hostHop) {
		if (hostHop != null && hostHop.getName() != null && hostHop.getIp() != null) {
			ipGeoLocationService.getLocalOrRemote(hostHop.getIp());
		}
	}
}
