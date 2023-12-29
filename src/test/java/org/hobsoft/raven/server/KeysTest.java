package org.hobsoft.raven.server;

import java.util.Base64;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeysTest
{
	@Test
	public void canEncodePublicKeyToPem()
	{
		var publicKey = TestKeys.publicKey("alg", "fmt", Base64.getDecoder().decode(
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
