package org.telaside.spamlocator.service;

import java.security.InvalidParameterException;

import org.springframework.util.StringUtils;
import org.telaside.spamlocator.domain.HostHop;

public class HostHopFactory {
	
	static private LineInfoExtractor lineInfoExtractor = new LineInfoExtractor();
	
	static public HostHop buildHostHop(String line) {
		if (StringUtils.hasLength(line)) {
			String[] split = line.split(" ");
			String name = split[0].trim();
			String ip = lineInfoExtractor.extractIpAddress(line);
			String id = lineInfoExtractor.extraInternalId(line);
			return new HostHop(name, ip, id);
		}
		throw new InvalidParameterException("cannot build HostHop on a null line");
	}
}
