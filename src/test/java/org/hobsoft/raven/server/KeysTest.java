package org.hobsoft.raven.server;

import java.security.PublicKey;
import java.util.Base64;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KeysTest
{
	@Test
	public void canEncodePublicKeyToPem()
	{
		var publicKey = mock(PublicKey.class);
		when(publicKey.getEncoded()).thenReturn(Base64.getDecoder().decode(
			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
			+ "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
			+ "ABCD"
		));

		var pem = Keys.toPem(publicKey);
		
		assertEquals(
			"""
			-----BEGIN PUBLIC KEY-----
			ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/
			ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/
			ABCD
			-----END PUBLIC KEY-----
			""",
			pem
		);
	}
}
