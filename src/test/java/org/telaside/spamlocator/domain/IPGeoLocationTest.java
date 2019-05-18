package org.telaside.spamlocator.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.math.BigDecimal;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class IPGeoLocationTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(IPGeoLocationTest.class);
	
	@Test
	public void canBuildIPGeoLocationWithJsonStream() throws Exception {
		ClassPathResource ipStackSample = new ClassPathResource("ipstack-sample.json");
		File input = ipStackSample.getFile();
		LOG.info("Reading ipstack json from {}", input.getAbsolutePath());
		
		IPGeoLocation sample = IPGeoLocation.buildFromReader(new FileReader(input));
		
		assertNotNull(sample);
		
		LOG.info("IPGeoLocation read is {}", sample);
		
		assertEquals(sample.getCity(), "Paris");
		assertEquals(sample.getContinentCode(), "EU");
		assertEquals(sample.getContinentName(), "Europe");
		assertEquals(sample.getCountryCode(), "FR");
		assertEquals(sample.getCountryName(), "France");
		assertEquals(sample.getIp(), "51.158.64.220");
		assertEquals(sample.getLatitude().compareTo(new BigDecimal("48.8607")), 0);
		assertEquals(sample.getLongitude().compareTo(new BigDecimal("2.3281")), 0);
		assertEquals(sample.getRegionCode(), "IDF");
		assertEquals(sample.getRegionName(), "Île-de-France");
		assertEquals(sample.getType(), "ipv4");
		assertEquals(sample.getZip(), "75001");
	}
}
