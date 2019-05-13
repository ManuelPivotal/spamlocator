package org.telaside.spamlocator.domain;

import com.google.common.base.MoreObjects;

public class HostHop {
	private String name;
	private String ip;
	
	public HostHop(String name, String ip) {
		this.name = name;
		this.ip = ip;
	}
	
	public String getName() {
		return name;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("ip", ip)
				.toString();
	}
}
