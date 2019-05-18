package org.telaside.spamlocator.transform;

import java.util.Enumeration;

import javax.mail.Header;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.transformer.GenericTransformer;
import org.telaside.spamlocator.domain.SpamLocatorMessage;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

public class IntegrationMimeMessageToSpamLocator implements GenericTransformer<MimeMessage, SpamLocatorMessage>{

	private static final Logger LOG = LoggerFactory.getLogger(IntegrationMimeMessageToSpamLocator.class);
	
	@Override
	public SpamLocatorMessage transform(MimeMessage source) {
		try {
			LOG.info(" ------------- Received {}", source);
			Enumeration<Header> allHeaders = source.getAllHeaders();
			ListMultimap<String, String> headers = MultimapBuilder
													.hashKeys()
													.arrayListValues()
													.build();
			
			while(allHeaders.hasMoreElements()) {
				Header header = allHeaders.nextElement();
				LOG.info("Header {}={}", header.getName(), header.getValue());
				headers.put(header.getName(), header.getValue());
			}
			
			SpamLocatorMessage locatorMessage = SpamLocatorMessage.newBuilder()
							.withHeaders(headers)
							.withMessageId(source.getMessageID())
							.withSubject(source.getSubject())
							.withReplyToDateSentAndSender(source.getReplyTo(), source.getSentDate(), source.getSender())
							.build();
			LOG.info("Returning {}", locatorMessage);
			return locatorMessage;
		} catch(Exception e) {
			LOG.error("Error while transforming message {}", source, e);
			throw new RuntimeException(e);
		}
	}
}
