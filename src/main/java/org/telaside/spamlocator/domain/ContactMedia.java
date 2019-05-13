package org.telaside.spamlocator.domain;

import com.google.common.base.MoreObjects;

public class ContactMedia<T> {
	
	private ContactMediaType mediaType;
	private T mediaDetails;
	
	public ContactMediaType getMediaType() {
		return mediaType;
	}
	public void setMediaType(ContactMediaType mediaType) {
		this.mediaType = mediaType;
	}
	
	public T getMediaDetails() {
		return mediaDetails;
	}
	public void setMediaDetails(T mediaDetails) {
		this.mediaDetails = mediaDetails;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("mediaType", mediaType)
				.add("mediaDetails", mediaDetails)
				.toString();
	}
}
