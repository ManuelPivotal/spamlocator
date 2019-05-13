package org.telaside.spamlocator.handler;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telaside.spamlocator.domain.HostHop;
import org.telaside.spamlocator.domain.ReceivedHeader;
import org.telaside.spamlocator.domain.SpamLocatorMessage;

import com.google.common.collect.Sets;

public class NetworkInformationHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(NetworkInformationHandler.class);
	private Set<String> names = Sets.newHashSet();
	private int index = 0;
	
	public SpamLocatorMessage handle(SpamLocatorMessage source) {
		for (ReceivedHeader receivedHeader : source.getReceivedHeaders()) {
			enrichHostHop(receivedHeader.getFrom());
		}
		return source;
	}
	
	private void enrichHostHop(HostHop hostHop) {
		index++;
		if (hostHop != null && hostHop.getName() != null && hostHop.getIp() == null) {
			String name = hostHop.getName();
			if (names.contains(name)) {
				return;
			}
			if ("localhost".equals(name)) {
				index--;
				return;
			}
			names.add(name);
			LOG.info("{}/{} - {} has no ip set", names.size(), index, hostHop.getName());
		}
	}
}
