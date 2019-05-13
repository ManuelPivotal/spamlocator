package org.telaside.spamlocator.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telaside.spamlocator.service.IpAddressExtractor;

public class IpAddressValidatorTest {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(IpAddressValidatorTest.class);
	
	@Test
	public void canExtractRightIPAddressFromString() {
		IpAddressExtractor underTest = new IpAddressExtractor();
		
		String found0 = underTest.extractIpAddress("from mail-green-antispam1.nfrance.com ([192.168.42.30])");
		assertEquals("192.168.42.30", found0);
		
		String found1 = underTest.extractIpAddress("from mail198.suw101.mcdlv.net (mail198.suw101.mcdlv.net [198.2.184.198])");
		assertEquals("198.2.184.198", found1);
		
		// incomplete
		String notFound0 = underTest.extractIpAddress("from mail-green-antispam1.nfrance.com ([192.168])");
		assertNull(notFound0);
		
		String notFound1 = underTest.extractIpAddress(null);
		assertNull(notFound1);

		// invalid
		String notFound2 = underTest.extractIpAddress("from mail-green-antispam1.nfrance.com ([192.280.42.30])");
		assertNull(notFound2);

	}
}
