package org.telaside.spamlocator.filter;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

import javax.mail.Header;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.core.GenericSelector;
import org.telaside.spamlocator.domain.SpamLocatorMessage;

public class SpamLocatorValidFilter implements GenericSelector<SpamLocatorMessage> {
	
	private static final Logger LOG = LoggerFactory.getLogger(SpamLocatorValidFilter.class);

	@Override
	public boolean accept(SpamLocatorMessage source) {
		boolean isValid = source.getMessageId() != null;
		if(!isValid) {
			dumpMessage(source);
		}
		return isValid;
	}
	
	private void dumpMessage(SpamLocatorMessage source) {
		LOG.error(" ---- Empty message id - dumping headers.");
		LOG.error("      -- headers");
		Map<String, Collection<String>> headers = source.getHeaders();
		headers.forEach((k, v) -> {
			LOG.error(" {}={}", k, v);
		});
	}
}
