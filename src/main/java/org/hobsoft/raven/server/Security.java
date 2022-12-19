package org.hobsoft.raven.server;

import java.net.URI;

public final class Security
{
	private Security()
	{
		throw new AssertionError();
	}
	
	public static final URI CONTEXT = URI.create("https://w3id.org/security/v1");
	
	public record PublicKey(URI id, URI owner, String publicKeyPem)
	{
	}
}
