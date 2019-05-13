package org.telaside.spamlocator.domain;

public enum ContactMediaType {
	Email("Email");
	
	private final String mediaType;
	
	private ContactMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	
	@Override
	public String toString() {
		return mediaType;
	}
}
