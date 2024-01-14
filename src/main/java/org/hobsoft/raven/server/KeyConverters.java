package org.hobsoft.raven.server;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

public final class KeyConverters
{
	private KeyConverters()
	{
		throw new AssertionError();
	}
	
	@WritingConverter
	public static class PublicKeyToPemConverter implements Converter<PublicKey, String>
	{
		@Override
		public String convert(PublicKey publicKey)
		{
			return Keys.toPem(publicKey);
		}
	}
	
	@ReadingConverter
	public static class PemToPublicKeyConverter implements Converter<String, PublicKey>
	{
		@Override
		public PublicKey convert(String pem)
		{
			return Keys.toPublicKey(pem);
		}
	}
	
	@WritingConverter
	public static class PrivateKeyToPemConverter implements Converter<PrivateKey, String>
	{
		@Override
		public String convert(PrivateKey privateKey)
		{
			return Keys.toPem(privateKey);
		}
	}
	
	@ReadingConverter
	public static class PemToPrivateKeyConverter implements Converter<String, PrivateKey>
	{
		@Override
		public PrivateKey convert(String pem)
		{
			return Keys.toPrivateKey(pem);
		}
	}
}
