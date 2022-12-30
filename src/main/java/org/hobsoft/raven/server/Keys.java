package org.hobsoft.raven.server;

import java.security.PublicKey;
import java.util.Base64;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public final class Keys
{
	private static final String BEGIN_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----";
	
	private static final String END_PUBLIC_KEY = "-----END PUBLIC KEY-----";
	
	private static final int LINE_LENGTH = 64;
	
	private static final String EOL = "\n";
	
	private Keys()
	{
		throw new AssertionError();
	}
	
	public static String toPem(PublicKey publicKey)
	{
		var encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
		
		return Stream.of(Stream.of(BEGIN_PUBLIC_KEY), wrap(encodedPublicKey, LINE_LENGTH), Stream.of(END_PUBLIC_KEY))
			.flatMap(stream -> stream)
			.collect(joining(EOL, "", EOL));
	}

	private static Stream<String> wrap(String string, int length)
	{
		return IntStream.range(0, string.length() / length + 1)
			.map(lineNumber -> lineNumber * length)
			.mapToObj(index -> string.substring(index, Math.min(index + length, string.length())));
	}
}
