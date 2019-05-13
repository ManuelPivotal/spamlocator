package org.telaside.spamlocator.service;

import java.security.InvalidParameterException;

import org.springframework.util.StringUtils;
import org.telaside.spamlocator.domain.HostHop;

public class HostHopFactory {
	
	static private IpAddressExtractor ipAddressValidator = new IpAddressExtractor();
	
	static public HostHop buildHostHop(String line) {
		if (StringUtils.hasLength(line)) {
			String[] split = line.split(" ");
			String name = split[0].trim();
			String ip = ipAddressValidator.extractIpAddress(line);
			return new HostHop(name, ip);
		}
		throw new InvalidParameterException("cannot build HostHop on a null line");
	}
}
