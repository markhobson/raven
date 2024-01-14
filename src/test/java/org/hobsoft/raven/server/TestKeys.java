package org.hobsoft.raven.server;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public final class TestKeys
{
	public static final BigInteger MODULUS = new BigInteger(
		"82719515628346151888518133309127229208262384693166362961364065227753553012424810"
			+ "53496637775278747833239359870454323176935092388331541348152690531436745909"
	);
	
	public static final BigInteger PUBLIC_EXPONENT = new BigInteger("65537");
	
	public static final BigInteger PRIVATE_EXPONENT = new BigInteger(
		"34829867613168318070153621445372322352289554077970089963209200603626337723390023"
			+ "5017068570039514324239886778458936329786650342752521504161607796764600043"
	);
	
	public static final String PUBLIC_KEY_PEM =
		"""
		-----BEGIN PUBLIC KEY-----
		MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJ3wdP4ZlZEBTfoD35E+F/Kvltc+vM2t
		BmX3Bh4FI5nv2L2K7WdvA+NELqVHqxnomqLn8nfnvsnh+Ff6XYXqVLUCAwEAAQ==
		-----END PUBLIC KEY-----
		""";
	
	public static final String PRIVATE_KEY_PEM =
		"""
		-----BEGIN PRIVATE KEY-----
		MIGyAgEAMA0GCSqGSIb3DQEBAQUABIGdMIGaAgEAAkEAnfB0/hmVkQFN+gPfkT4X
		8q+W1z68za0GZfcGHgUjme/YvYrtZ28D40QupUerGeiaoufyd+e+yeH4V/pdhepU
		tQIBAAJABqZyzqY2IYJsDZxvI/b43CufjtWh7okcDVwO4mG2A4vAPI1ITVkhVImc
		hx/09QV3tb+kCFuHxMxP+TtEV5Zm6wIBAAIBAAIBAAIBAAIBAA==
		-----END PRIVATE KEY-----
		""";
	
	private TestKeys()
	{
		throw new AssertionError();
	}
	
	public static PublicKey publicKey() throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		return getKeyFactory().generatePublic(new RSAPublicKeySpec(MODULUS, PUBLIC_EXPONENT));
	}
	
	public static PrivateKey privateKey() throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		return getKeyFactory().generatePrivate(new RSAPrivateKeySpec(MODULUS, PRIVATE_EXPONENT));
	}
	
	private static KeyFactory getKeyFactory() throws NoSuchAlgorithmException
	{
		return KeyFactory.getInstance("RSA");
	}
}
