package org.telaside.spamlocator.domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

@Entity
@Table(name = "spam_locator")
public class SpamLocatorMessage {
	
	static public final String RECEIVED_HEADER_FIELD = "Received";
	
	@Id
	private String messageId;
	
	@Column(name = "reply_to")
	private String replyTo;
	
	@Column(name = "e_sender")
	private String sender;

	@Column(name = "sent_date")
	@Type(type="timestamp")
	private Date sentDate;
	
	@OneToMany(cascade = CascadeType.ALL)
	@OrderColumn(name = "received_header_index")
	private List<ReceivedHeader> receivedHeaders = Lists.newArrayList();
	
	@Column(name = "email_subject")
	private String subject;

	@Transient
	private Map<String, Collection<String>> headers;	
	
	public List<ReceivedHeader> getReceivedHeaders() {
		return receivedHeaders;
	}
	
	public String getMessageId() {
		return messageId;
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
		
		private static final Logger LOG = LoggerFactory.getLogger(Builder.class);
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
		
		public Builder withSubject(String subject) {
			built.subject = subject;
			return this;
		}

		public Builder withMessageId(String messageId) {
			built.messageId = messageId;
			if (messageId == null) {
				LOG.error(" --> messageId is null");
			}
			return this;
		}
		
		public Builder withReplyToDateSentAndSender(Address[] repliesTo, Date sentDate, Address sender) {
			if (repliesTo != null && repliesTo.length > 0) {
				Address replyTo = repliesTo[0];
				if (replyTo instanceof InternetAddress) {
					InternetAddress internetAddress = (InternetAddress)replyTo;
					built.replyTo = internetAddress.getAddress();
					LOG.info(">>>> reply address {}", built.replyTo);
				}
			}
			built.sentDate = sentDate;
			if(sender != null && sender instanceof InternetAddress) {
				InternetAddress internetAddress = (InternetAddress)sender;
				built.sender = internetAddress.getAddress();
			}
			return this;
		}

		public SpamLocatorMessage build() {
			Assert.notNull(built.headers, "header cannot be null while building spam locator object");
			built.extractHeaders();
			return built;
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
