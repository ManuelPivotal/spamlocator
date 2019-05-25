package org.telaside.spamlocator.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class LineInfoExtractor {
	
	private static final Logger LOG = LoggerFactory.getLogger(LineInfoExtractor.class);

	private static final String IPADDRESS_PATTERN = "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." 
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
	private Pattern ipAddressPattern = Pattern.compile(IPADDRESS_PATTERN);
	
	private static final String WIHT_LMTP_ID = "with[\\s]+LMTP[\\s]+id[\\s]+([\\S]*)";
	private static final Pattern lmtpIdPattern = Pattern.compile(WIHT_LMTP_ID);
	
	private static final String WITH_ESMTP_PATTERN = "with[\\s]+ESMTP[\\s]+id[\\s]+([\\S]*)";
	private static final Pattern esmtpIdPattern = Pattern.compile(WITH_ESMTP_PATTERN);

	public LineInfoExtractor() {
	}

	public String extractIpAddress(String ip ) {
		if (StringUtils.hasLength(ip)) {
			Matcher matcher = ipAddressPattern.matcher(ip);
			if (matcher.find()) {
				int groupCount = matcher.groupCount();
				if (LOG.isDebugEnabled()) {
					LOG.debug("Found IP in {}", ip);
					for(int index = 0; index < groupCount; index++) {
						LOG.info("Matcher group {} index {}", matcher.group(index), index);
					}
				}
				return matcher.group(0);
			}
		}
		LOG.debug("No IP pattern found in {}", ip);
		return null;
	}

	public String extraInternalId(String line) {
		if (StringUtils.hasLength(line)) {
			String id = extractdId(lmtpIdPattern, line);
			if (id != null) {
				return id;
			}
			return extractdId(esmtpIdPattern, line);
		}
		LOG.debug("No ESMTP nor LMTP pattern found in {}", line);
		return null;
	}

	private String extractdId(Pattern pattern, String line) {
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			int groupCount = matcher.groupCount();
			if (LOG.isDebugEnabled()) {
				LOG.debug("Found pattern in {}", line);
				for(int index = 0; index < groupCount; index++) {
					LOG.info("Matcher group {} index {}", matcher.group(index), index);
				}
			}
			return matcher.group(1);
		}
		return null;
	}
}
