package org.telaside.spamlocator.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.telaside.spamlocator.domain.SpamLocatorMessage;
import org.telaside.spamlocator.service.SpamLocatorService;

public class SpamLocatorHandler {
	
	@Autowired
	private SpamLocatorService spamLocatorService;
	
	public SpamLocatorMessage save(SpamLocatorMessage spamLocatorMessage) {
		spamLocatorService.save(spamLocatorMessage);
		return spamLocatorMessage;
	}
}
