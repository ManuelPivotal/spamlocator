package org.telaside.spamlocator.domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;

@Entity
@Table(name = "spam_locator")
public class SpamLocatorMessage {
	
	private static final Logger LOG = LoggerFactory.getLogger(SpamLocatorMessage.class);
	
	static public final String RECEIVED_HEADER_FIELD = "Received";
	static public final String RETURN_PATH = "Return-Path";
	
	@Id
	@Column(name = "id")
	private String messageId;
	
	@Column(name = "reply_to")
	private String replyTo;
	
	@Column(name = "e_sender")
	private String sender;

	@Column(name = "sent_date")
	@Type(type="timestamp")
	private Date sentDate;
	
	@Column(name = "recorded_date")
	@Type(type="timestamp")
	private Date recordedDate;

	@Column(name = "return_path")
	private String returnPath;
	
	@OneToMany(cascade = CascadeType.ALL)
	@OrderColumn(name = "received_header_index")
	@JoinTable(name = "spam_locator_received_header",
		joinColumns = @JoinColumn(
	            name = "spam_locator_id",
	            referencedColumnName = "id"
	    ),
	    inverseJoinColumns = @JoinColumn(
	            name = "received_header",
	            referencedColumnName = "id"
    ))
	private List<ReceivedHeader> receivedHeaders = Lists.newArrayList();
	
	@Column(name = "email_subject")
	private String subject;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "spam_locator_header_entry",
			joinColumns = @JoinColumn(
	                name = "spam_locator_id",
	                referencedColumnName = "id"
	        ),
	        inverseJoinColumns = @JoinColumn(
	                name = "header_entry_id",
	                referencedColumnName = "id"
	))
	private Set<HeaderEntry> headerEntries;

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
		
		public Builder withHeaders(SetMultimap<String, String> headers) {
			built.headers = headers.asMap();
			return this;
		}
		
		public Builder withSubject(String subject) {
			built.subject = subject;
			return this;
		}

		public Builder withMessageIdAndReturnPath(String messageId, String returnPath) {
			built.messageId = messageId;
			if (messageId == null) {
				LOG.warn("messageId is null");
			}
			built.returnPath = returnPath;
			return this;
		}
		
		public Builder withReplyToDateSentAndSender(Address[] repliesTo, Date sentDate, Address sender) {
			if (repliesTo != null && repliesTo.length > 0) {
				Address replyTo = repliesTo[0];
				if (replyTo instanceof InternetAddress) {
					InternetAddress internetAddress = (InternetAddress)replyTo;
					built.replyTo = internetAddress.getAddress();
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
			built.buildHeaderEntrySet();
			built.recordedDate = new Date();
			built.induceMessage();
			return built;
		}
	}
	
	public void extractHeaders() {
		headers.get(RECEIVED_HEADER_FIELD).forEach((received) -> {
			receivedHeaders.add(ReceivedHeader.buildFromHeader(received));
		});
	}

	public void induceMessage() {
		if (messageId == null) {
			LOG.debug("Trying to induce messageid from received headers");
			List<String> ids = Lists.newArrayList();
			receivedHeaders.forEach(header -> {
				HostHop hostHop = header.getBy();
				if(hostHop != null) {
					String byId = hostHop.getById();
					if(byId != null) {
						ids.add(byId);
					}
				}
			});
			if(!ids.isEmpty()) {
				messageId = String.join("-", ids);
				LOG.warn("Inducing message id to {}", messageId);
				return;
			}
			LOG.error("Cannot induce message id");
		}
	}

	public void buildHeaderEntrySet() {
		headerEntries = Sets.newHashSet();
		headers.forEach((key, values) -> {
			values.forEach(value -> {
				headerEntries.add(new HeaderEntry(key, value));
			});
		});
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
