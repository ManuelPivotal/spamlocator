package org.telaside.spamlocator.transform;

import static org.springframework.util.StringUtils.hasLength;

import java.util.Enumeration;

import javax.mail.Header;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.util.StringUtils;
import org.telaside.spamlocator.domain.SpamLocatorMessage;

import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;

public class IntegrationMimeMessageToSpamLocator implements GenericTransformer<MimeMessage, SpamLocatorMessage>{

	private static final Logger LOG = LoggerFactory.getLogger(IntegrationMimeMessageToSpamLocator.class);
	
	@Override
	public SpamLocatorMessage transform(MimeMessage source) {
		try {
			LOG.debug(" ------------- Received {}", source);
			Enumeration<Header> allHeaders = source.getAllHeaders();
			SetMultimap<String, String> headers = MultimapBuilder
													.hashKeys()
													.hashSetValues()
													.build();
			
			while(allHeaders.hasMoreElements()) {
				Header header = allHeaders.nextElement();
				if(LOG.isDebugEnabled()) {
					LOG.debug("Header {}={}", header.getName(), header.getValue());
				}
				headers.put(header.getName(), header.getValue());
			}
			
			SpamLocatorMessage locatorMessage = SpamLocatorMessage.newBuilder()
							.withHeaders(headers)
							.withMessageIdAndReturnPath(source.getMessageID(), source.getHeader("Return-Path", ";"))
							.withSubject(source.getSubject())
							.withReplyToDateSentAndSender(source.getReplyTo(), source.getSentDate(), source.getSender())
							.build();
			LOG.debug("Returning {}", locatorMessage);
			return locatorMessage;
		} catch(Exception e) {
			LOG.error("Error while transforming message {}", source, e);
			throw new RuntimeException(e);
		}
	}
}
