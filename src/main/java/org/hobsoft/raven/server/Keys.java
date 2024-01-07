package org.hobsoft.raven.server;

import java.io.IOException;
import java.io.StringWriter;
import java.security.PublicKey;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

public final class Keys
{
	private Keys()
	{
		throw new AssertionError();
	}
	
	public static String toPem(PublicKey publicKey)
	{
		var writer = new StringWriter();
		
		try (var pemWriter = new PemWriter(writer))
		{
			pemWriter.writeObject(new PemObject("PUBLIC KEY", publicKey.getEncoded()));
		}
		catch (IOException exception)
		{
			throw new RuntimeException("Error writing PEM", exception);
		}
		
		return writer.toString();
	}
}
