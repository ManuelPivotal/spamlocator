package org.telaside.spamlocator.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.telaside.spamlocator.domain.SpamLocatorMessage;
import org.telaside.spamlocator.repository.SpamLocatorRepository;

public class SpamLocatorService {
	
	private static final Logger LOG = LoggerFactory.getLogger(SpamLocatorService.class);

	@Autowired
	private SpamLocatorRepository spamLocatorRepository;
	
	@Transactional(readOnly = false)
	public void save(SpamLocatorMessage spamLocatorMessage) {
		if (spamLocatorMessage.getMessageId() != null) {
			LOG.debug("Saving {}", spamLocatorMessage);
			spamLocatorRepository.save(spamLocatorMessage);
			return;
		}
		LOG.error("Skipping {} - no message id", spamLocatorMessage.getSubject());
	}

	@Transactional(readOnly = true)
	public boolean exists(SpamLocatorMessage spamLocatorMessage) {
		String messageId = spamLocatorMessage.getMessageId();
		boolean exists = messageId != null && spamLocatorRepository.existsById(messageId);
		LOG.debug("Message {} already exists is {}", messageId, exists);
		return exists;
	}
	
	public Set<String> findUnsetIpGeolocation() {
		return spamLocatorRepository.findUnsetIpGeolocation();
	}
}
