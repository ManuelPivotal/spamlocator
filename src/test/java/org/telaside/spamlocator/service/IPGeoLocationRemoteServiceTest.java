package org.telaside.spamlocator.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.telaside.spamlocator.domain.IPGeoLocation;

public class IPGeoLocationRemoteServiceTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(IPGeoLocationRemoteServiceTest.class);
	
	@Test
	public void canCallRemoteServiceWithCorrectIp() throws Exception {
		IPGeoLocationRemoteService underTest = new IPGeoLocationRemoteService();
		ReflectionTestUtils.setField(underTest, "ipstackURLTemplate", "http://api.ipstack.com/%s?access_key=4e29f7bab704618e85540ac242154fdd");
	
		IPGeoLocation returned = underTest.getRemoteGeoLocationFor("51.158.64.220");
		
		LOG.info("Remote service returned {}", returned);
		assertNotNull(returned);
	}
	
	@Test
	public void canCallRemoteServiceWithUncorrectIp() throws Exception {
		IPGeoLocationRemoteService underTest = new IPGeoLocationRemoteService();
		ReflectionTestUtils.setField(underTest, "ipstackURLTemplate", "http://api.ipstack.com/%s?access_key=4e29f7bab704618e85540ac242154fdd");
	
		IPGeoLocation returned = underTest.getRemoteGeoLocationFor("333.0.0.1");
		
		LOG.info("Remote service returned {}", returned);
		assertNotNull(returned);
	}
}
