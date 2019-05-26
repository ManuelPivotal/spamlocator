package org.telaside.spamlocator.domain;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

import java.io.Reader;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "ip_geo_location")
public class IPGeoLocation {
	
	private static final Logger LOG = LoggerFactory.getLogger(IPGeoLocation.class);
	
	private static final ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	@Id
	private String ip;
	
	@Column(name="geo_org")
	private String organisation;
	
	@Column(name="ip_type")
	private String type;
	
	@JsonProperty(value = "continent_code")
	@Column(name = "continent_code")
	private String continentCode;
	
	@JsonProperty(value = "continent_name")
	@Column(name = "continent_name")
	private String continentName;

	@JsonProperty(value = "country_code")
	@Column(name = "country_code")
	private String countryCode;
	
	@JsonProperty(value = "country_name")
	@Column(name = "country_name")
	private String countryName;
	
	@JsonProperty(value = "region_code")
	@Column(name = "region_code")
	private String regionCode;
	
	@JsonProperty(value = "region_name")
	@Column(name = "region_name")
	private String regionName;
	
	@Column(name = "geo_city")
	private String city;
	
	@Column(name = "geo_zip")
	private String zip;
	
	@Column(name = "geo_latitude")
	private BigDecimal latitude;

	@Column(name = "geo_longitude")
	private BigDecimal longitude;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getContinentCode() {
		return continentCode;
	}
	public void setContinentCode(String continentCode) {
		this.continentCode = continentCode;
	}
	
	public String getContinentName() {
		return continentName;
	}
	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	
	public static IPGeoLocation buildFromReader(Reader reader) {
		try {
			return mapper.readValue(reader, IPGeoLocation.class);
		} catch (Exception e) {
			LOG.error("Cannot parse IPGeoLocation from {}", reader, e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("ip", ip)
				.add("type", type)
				.add("continentCode", continentCode)
				.add("continentName", continentName)
				.add("countryCode", countryCode)
				.add("countryName", countryName)
				.add("city", city)
				.add("zip", zip)
				.add("latitude", latitude)
				.add("longitude", longitude)
				.toString();
	}
}
