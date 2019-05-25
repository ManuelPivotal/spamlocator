package org.telaside.spamlocator.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.GenericSelector;
import org.telaside.spamlocator.domain.SpamLocatorMessage;
import org.telaside.spamlocator.service.SpamLocatorService;

public class SpamLocatorDuplicateFilter implements GenericSelector<SpamLocatorMessage> {
	
	@Autowired
	private SpamLocatorService spamLocatorService;

	@Override
	public boolean accept(SpamLocatorMessage source) {
		return !spamLocatorService.exists(source);
	}
}
