package org.telaside.spamlocator.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceivedHeaderTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(ReceivedHeaderTest.class);

	@Test
	public void canBuildFromHeader() throws Exception {
		String receivedheader = "from mail-blue-antispam1.nfrance.com ([192.168.42.31])\r\n" + 
				"	by mail-green-filer1.lan.nfrance.net with LMTP id 2IvlECaHrVxKbgAAGvI7SA\r\n" + 
				"	for <pop124924@mail-green.nfrance.com>; Wed, 10 Apr 2019 08:03:18 +0200";
		
		ReceivedHeader underTest = ReceivedHeader.buildFromHeader(receivedheader);
		
		LOG.info("Received header {}", underTest);
		
		assertEquals("mail-blue-antispam1.nfrance.com", underTest.getFrom());
		assertEquals("mail-green-filer1.lan.nfrance.net", underTest.getBy());
		assertEquals("pop124924@mail-green.nfrance.com", underTest.getMailFor());
	}
}
