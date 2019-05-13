package org.telaside.spamlocator.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.security.InvalidParameterException;

import org.junit.Test;
import org.telaside.spamlocator.domain.HostHop;

public class HostHopFactoryTest {

	@Test(expected = InvalidParameterException.class)
	public void canThrowExceptionWhenNullParam() {
		HostHopFactory.buildHostHop(null);
	}
	
	@Test
	public void canBuildHostHop() {
		HostHop returned = HostHopFactory.buildHostHop("mail-green-antispam1.nfrance.com ([192.168.42.30]))");
		assertEquals("mail-green-antispam1.nfrance.com", returned.getName());
		assertEquals("192.168.42.30", returned.getIp());
		
		returned = HostHopFactory.buildHostHop("mail-green-filer1.lan.nfrance.net with LMTP id +N2zNPCpq1z+NAAAGvI7SA");
		assertEquals("mail-green-filer1.lan.nfrance.net", returned.getName());
		assertNull(returned.getIp());
	}
}
