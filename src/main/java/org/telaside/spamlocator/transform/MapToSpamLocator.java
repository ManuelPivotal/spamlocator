package org.telaside.spamlocator.transform;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.transformer.GenericTransformer;
import org.telaside.spamlocator.domain.SpamLocatorMessage;

@SuppressWarnings("rawtypes")
public class MapToSpamLocator implements GenericTransformer<Map, SpamLocatorMessage> {
	
	private static final Logger LOG = LoggerFactory.getLogger(MapToSpamLocator.class);
	
	@Override
	public SpamLocatorMessage transform(Map source) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("{} - Received {}", source.get("subject"));
		}
		
		SpamLocatorMessage builtFromMap = SpamLocatorMessage.newBuilder()
				.withHeaders((Map) source.get("headers"))
				.withSubject((String) source.get("subject"))
				.build();
		return builtFromMap;
	}
}
