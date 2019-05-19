package org.telaside.spamlocator.transform;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.util.CollectionUtils;
import org.telaside.spamlocator.domain.SpamLocatorMessage;

@SuppressWarnings("rawtypes")
public class MapToSpamLocator implements GenericTransformer<Map, SpamLocatorMessage> {
	
	private static final Logger LOG = LoggerFactory.getLogger(MapToSpamLocator.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public SpamLocatorMessage transform(Map source) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("{} - Received {}", source.get("subject"));
		}
		
		String messageId = extractFromList((List<String>)(((Map)source.get("headers"))).get("Message-ID"));
		if(messageId == null) {
			messageId = extractFromList((List<String>)(((Map)source.get("headers"))).get("Message-Id"));
		}
		
		SpamLocatorMessage builtFromMap = SpamLocatorMessage.newBuilder()
				.withHeaders((Map) source.get("headers"))
				.withSubject((String) source.get("subject"))
				.withMessageIdAndReturnPath(messageId, extractFromList((List<String>)(((Map)source.get("headers"))).get("Return-Path")))
				.build();
		return builtFromMap;
	}
	
	private String extractFromList(List<String> values) {
		return CollectionUtils.isEmpty(values) ? null : values.get(0);
	}
}
