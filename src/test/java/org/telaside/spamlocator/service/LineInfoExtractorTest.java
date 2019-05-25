package org.telaside.spamlocator.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telaside.spamlocator.service.LineInfoExtractor;

public class LineInfoExtractorTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(LineInfoExtractorTest.class);
	
	@Test
	public void canExtractRightIdFromLMTPLine() {
		LineInfoExtractor underTest = new LineInfoExtractor();
		
		String lmtpLine0 = "with LMTP id eDiwJrZM3VzJZwAAGvI7SA";
		String lmtpId0 = underTest.extraInternalId(lmtpLine0);
		LOG.info("1. id {} in {}", lmtpId0, lmtpLine0);
		assertEquals("eDiwJrZM3VzJZwAAGvI7SA", lmtpId0);
		
		String lmtpLine1 = "with LMTP id eDiwJrZ+M3VzJZwAAGvI7SA";
		String lmtpId1 = underTest.extraInternalId(lmtpLine1);
		LOG.info("2. id {} in {}", lmtpId1, lmtpLine1);
		assertEquals("eDiwJrZ+M3VzJZwAAGvI7SA", lmtpId1);

		String esmtpLine0 = "with ESMTP id 8762B1416C3";
		String esmtpId0 = underTest.extraInternalId(esmtpLine0);
		LOG.info("3. id {} in {}", esmtpId0, esmtpLine0);
		assertEquals("8762B1416C3", esmtpId0);
		
		String esmtpLine1 = "with ESMTP id 8762B14+16C3";
		String esmtpId1 = underTest.extraInternalId(esmtpLine1);
		LOG.info("4. id {} in {}", esmtpId1, esmtpLine1);
		assertEquals("8762B14+16C3", esmtpId1);

	}
	
	@Test
	public void canExtractRightIPAddressFromString() {
		LineInfoExtractor underTest = new LineInfoExtractor();
		
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
