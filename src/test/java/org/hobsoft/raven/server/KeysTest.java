package org.hobsoft.raven.server;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeysTest
{
	private static final BigInteger MODULUS = new BigInteger(
		"82719515628346151888518133309127229208262384693166362961364065227753553012424810"
		+ "53496637775278747833239359870454323176935092388331541348152690531436745909"
	);
	
	private static final BigInteger PUBLIC_EXPONENT = new BigInteger("65537");
	
	private static final String PUBLIC_KEY_PEM =
		"""
		-----BEGIN PUBLIC KEY-----
		MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJ3wdP4ZlZEBTfoD35E+F/Kvltc+vM2t
		BmX3Bh4FI5nv2L2K7WdvA+NELqVHqxnomqLn8nfnvsnh+Ff6XYXqVLUCAwEAAQ==
		-----END PUBLIC KEY-----
		""";
	
	private KeyFactory keyFactory;
	
	@BeforeEach
	public void setUp() throws NoSuchAlgorithmException
	{
		keyFactory = KeyFactory.getInstance("RSA");
	}
	
	@Test
	public void canEncodePublicKeyToPem() throws InvalidKeySpecException
	{
		var publicKey = keyFactory.generatePublic(new RSAPublicKeySpec(MODULUS, PUBLIC_EXPONENT));

		var pem = Keys.toPem(publicKey);
		
		assertEquals(PUBLIC_KEY_PEM, pem);
	}
}
