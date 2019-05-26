package org.telaside.spamlocator.domain;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class IPInfoReturnedData {
	private String ip;
	
	@JsonProperty(value = "hostname")
	private String hostName;
	
	private String city;
	private String region;
	private String country;
	private String loc;
	private String postal;
	private String org;
	
	public String getIp() {
		return ip;
	}
	public String getHostName() {
		return hostName;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getRegion() {
		return region;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getLoc() {
		return loc;
	}
	
	public String getPostal() {
		return postal;
	}
	
	public String getOrg() {
		return org;
	}
	
	public IPGeoLocation buildIPGeoLocation() {
		IPGeoLocation ipGeoLocation = new IPGeoLocation();
		ipGeoLocation.setIp(ip);
		ipGeoLocation.setCity(city);
		//ipGeoLocation.setContinentCode(continentCode);
		//ipGeoLocation.setContinentName(continentName);
		ipGeoLocation.setCountryCode(country);
		//ipGeoLocation.setCountryName(countryName);
		if(StringUtils.hasLength(loc)) {
			String[] coordinates = loc.split(",");
			if(coordinates.length == 2) {
				ipGeoLocation.setLatitude(new BigDecimal(coordinates[0]));
				ipGeoLocation.setLongitude(new BigDecimal(coordinates[1]));
			}
		}
		ipGeoLocation.setOrganisation(org);
		//ipGeoLocation.setRegionCode(regionCode);
		ipGeoLocation.setRegionName(region);
		ipGeoLocation.setType("ipv4");
		ipGeoLocation.setZip(postal);
		return ipGeoLocation;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("city", city)
				.add("country", country)
				.add("hostName", hostName)
				.add("ip", ip)
				.add("loc", loc)
				.add("postal", postal)
				.add("region", region)
				.toString();
	}
}
