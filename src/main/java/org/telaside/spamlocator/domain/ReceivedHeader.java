package org.telaside.spamlocator.domain;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telaside.spamlocator.service.HostHopFactory;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

public class ReceivedHeader {
	
	private static final Logger LOG = LoggerFactory.getLogger(ReceivedHeader.class);
	
	private static final String FROM = "from ";
	private static final int FROM_LENGTH = FROM.length();
	
	private static final String BY = "by ";
	private static final int BY_LENGTH = BY.length();

	private static final String FOR = "for <";
	private static final int FOR_LENGTH = FOR.length();
	
	private HostHop from;
	private HostHop by;
	private String mailFor;

	public static ReceivedHeader buildFromHeader(String header) {
		ReceivedHeader receivedHeader = new ReceivedHeader();
		receivedHeader.buildFrom(header);
		return receivedHeader;
	}
	
	protected void buildFrom(String header) {
		extractFields(split(header));
	}

	protected List<String> split(String header) {
		List<String> split = Lists.newArrayList();
		String[] lines = header.split("\r\n");
		for (String line : lines) {
			split.add(line.replaceAll("^\t", ""));
		}
		return split;
	}
	
	protected void extractFields(List<String> split) {
		split.forEach((line) -> {
			LOG.debug("Extracting from line [{}]", line);
			if (line.startsWith(FROM)) {
				extractFrom(line.substring(FROM_LENGTH));
			} else if (line.startsWith(BY)) {
				extractBy(line.substring(BY_LENGTH));
			} else if (line.startsWith(FOR)) {
				extractFor(line.substring(FOR_LENGTH));
			}
		});
	}

	private void extractFor(String line) {
		String[] split = line.split(">");
		this.mailFor = split[0].trim();
	}

	private void extractBy(String line) {
		this.by = HostHopFactory.buildHostHop(line);
	}

	private void extractFrom(String line) {
		this.from = HostHopFactory.buildHostHop(line);
	}

	public HostHop getFrom() {
		return from;
	}

	public HostHop getBy() {
		return by;
	}

	public String getMailFor() {
		return mailFor;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("from", from)
				.add("by", by)
				.add("mailFor", mailFor)
				.toString();
	}
}
