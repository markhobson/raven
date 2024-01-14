package org.hobsoft.raven.server;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

public final class Keys
{
	private Keys()
	{
		throw new AssertionError();
	}
	
	public static String toPem(PublicKey publicKey)
	{
		return toPem(new PemObject("PUBLIC KEY", publicKey.getEncoded()));
	}
	
	public static RSAPublicKey toPublicKey(String pem)
	{
		var pemObject = fromPem(pem);

		try
		{
			var keySpec = new X509EncodedKeySpec(pemObject.getContent());
			return (RSAPublicKey) getKeyFactory().generatePublic(keySpec);
		}
		catch (NoSuchAlgorithmException | InvalidKeySpecException exception)
		{
			throw new RuntimeException("Error creating key", exception);
		}
	}
	
	public static String toPem(PrivateKey privateKey)
	{
		return toPem(new PemObject("PRIVATE KEY", privateKey.getEncoded()));
	}
	
	public static RSAPrivateKey toPrivateKey(String pem)
	{
		var pemObject = fromPem(pem);
		
		try
		{
			var keySpec = new PKCS8EncodedKeySpec(pemObject.getContent());
			return (RSAPrivateKey) getKeyFactory().generatePrivate(keySpec);
		}
		catch (NoSuchAlgorithmException | InvalidKeySpecException exception)
		{
			throw new RuntimeException("Error creating key", exception);
		}
	}
	
	private static String toPem(PemObject pemObject)
	{
		var writer = new StringWriter();
		
		try (var pemWriter = new PemWriter(writer))
		{
			pemWriter.writeObject(pemObject);
		}
		catch (IOException exception)
		{
			throw new RuntimeException("Error writing PEM", exception);
		}
		
		return writer.toString();
	}
	
	private static PemObject fromPem(String pem)
	{
		try (var pemReader = new PemReader(new StringReader(pem)))
		{
			return pemReader.readPemObject();
		}
		catch (IOException exception)
		{
			throw new RuntimeException("Error reading PEM", exception);
		}
	}
	
	private static KeyFactory getKeyFactory() throws NoSuchAlgorithmException
	{
		return KeyFactory.getInstance("RSA");
	}
}
