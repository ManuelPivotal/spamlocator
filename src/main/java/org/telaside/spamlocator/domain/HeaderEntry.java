package org.telaside.spamlocator.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

@Entity
@Table(name = "header_entry")
public class HeaderEntry {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
	
	@Column(name = "entry_name")
	private String name;

	@Column(name = "entry_value", length = 2000)
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public HeaderEntry() {
		
	}
	
	public HeaderEntry(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof HeaderEntry)) {
			return false;
		}

		if(obj == this) {
			return true;
		}
		
		HeaderEntry other = (HeaderEntry) obj;
		return Objects.equals(name, other.name)
				&& Objects.equals(value, other.value);
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("value", value)
				.toString();
	}
	
}
