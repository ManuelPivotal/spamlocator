package org.telaside.spamlocator.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class IpAddressExtractor {
	
	private static final Logger LOG = LoggerFactory.getLogger(IpAddressExtractor.class);

	private Pattern pattern;

	private static final String IPADDRESS_PATTERN = "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." 
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])";

	public IpAddressExtractor() {
		pattern = Pattern.compile(IPADDRESS_PATTERN);
	}

	public boolean validate(final String ip) {
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}
	
	public String extractIpAddress(String ip ) {
		if (StringUtils.hasLength(ip)) {
			Matcher matcher = pattern.matcher(ip);
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
}
