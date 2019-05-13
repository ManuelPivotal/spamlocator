package org.telaside.spamlocator.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

public class SpamLocatorMessage {
	
	static public final String RECEIVED_HEADER_FIELD = "Received";
	
	private Map<String, Collection<String>> headers;	
	private List<ReceivedHeader> receivedHeaders = Lists.newArrayList();
	private String subject;
	
	public List<ReceivedHeader> getReceivedHeaders() {
		return receivedHeaders;
	}
	
	public Map<String, Collection<String>> getHeaders() {
		return headers;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public static Builder newBuilder() {
		return new Builder();
	}
	
	public static class Builder {
		SpamLocatorMessage built = new SpamLocatorMessage();
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder withHeaders(Map headers) {
			built.headers = headers;
			return this;		
		}
		
		public Builder withHeaders(ListMultimap<String, String> headers) {
			built.headers = headers.asMap();
			return this;
		}
		
		public SpamLocatorMessage build() {
			Assert.notNull(built.headers, "header cannot be null while building spam locator object");
			built.extractHeaders();
			return built;
		}

		public Builder withSubject(String subject) {
			built.subject = subject;
			return this;
		}
	}
	
	public void extractHeaders() {
		headers.get(RECEIVED_HEADER_FIELD).forEach((received) -> {
			receivedHeaders.add(ReceivedHeader.buildFromHeader(received));
		});
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
