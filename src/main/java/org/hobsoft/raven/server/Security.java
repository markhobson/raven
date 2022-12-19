package org.hobsoft.raven.server;

import java.net.URI;

/**
 * Security Vocabulary for linked data.
 *
 * @see <a href="https://web-payments.org/vocabs/security">The Security Vocabulary</a>
 */
public final class Security
{
	private Security()
	{
		throw new AssertionError();
	}
	
	public static final URI CONTEXT = URI.create("https://w3id.org/security/v1");
	
	/**
	 * Public key property.
	 * 
	 * @see <a href="https://web-payments.org/vocabs/security#publicKey">publicKey</a>
	 */
	public record PublicKey(URI id, URI owner, String publicKeyPem)
	{
	}
}
