package org.hobsoft.raven.server;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeysTest
{
	@Test
	public void canEncodePublicKeyToPem() throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		var pem = Keys.toPem(TestKeys.publicKey());
		
		assertEquals(TestKeys.PUBLIC_KEY_PEM, pem);
	}
	
	@Test
	public void canDecodePublicKeyFromPem()
	{
		var publicKey = Keys.toPublicKey(TestKeys.PUBLIC_KEY_PEM);
		
		assertEquals(TestKeys.MODULUS, publicKey.getModulus());
		assertEquals(TestKeys.PUBLIC_EXPONENT, publicKey.getPublicExponent());
	}
	
	@Test
	public void canEncodePrivateKeyToPem() throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		var pem = Keys.toPem(TestKeys.privateKey());
		
		assertEquals(TestKeys.PRIVATE_KEY_PEM, pem);
	}
	
	@Test
	public void canDecodePrivateKeyFromPem()
	{
		var privateKey = Keys.toPrivateKey(TestKeys.PRIVATE_KEY_PEM);
		
		assertEquals(TestKeys.MODULUS, privateKey.getModulus());
		assertEquals(TestKeys.PRIVATE_EXPONENT, privateKey.getPrivateExponent());
	}
}
