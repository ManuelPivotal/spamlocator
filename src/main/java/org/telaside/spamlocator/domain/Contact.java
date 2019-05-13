package org.telaside.spamlocator.domain;

import java.util.List;

public class Contact {
	List<ContactMedia<String>> emails;


	public List<ContactMedia<String>> getEmails() {
		return emails;
	}

	public void setEmails(List<ContactMedia<String>> emails) {
		this.emails = emails;
	}
}
