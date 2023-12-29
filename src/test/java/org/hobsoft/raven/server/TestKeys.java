package org.hobsoft.raven.server;

import java.security.PrivateKey;
import java.security.PublicKey;

public final class TestKeys
{
	private TestKeys()
	{
		throw new AssertionError();
	}
	
	public static PublicKey publicKey(String algorithm, String format, byte[] encoded)
	{
		return new PublicKey()
		{
			@Override
			public String getAlgorithm()
			{
				return algorithm;
			}
			
			@Override
			public String getFormat()
			{
				return format;
			}
			
			@Override
			public byte[] getEncoded()
			{
				return encoded;
			}
		};
	}
	
	public static PrivateKey privateKey(String algorithm, String format, byte[] encoded)
	{
		return new PrivateKey()
		{
			@Override
			public String getAlgorithm()
			{
				return algorithm;
			}
			
			@Override
			public String getFormat()
			{
				return format;
			}
			
			@Override
			public byte[] getEncoded()
			{
				return encoded;
			}
		};
	}
}
