package org.hobsoft.raven.server;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Security Vocabulary for linked data.
 *
 * @see <a href="https://w3c-ccg.github.io/security-vocab">The Security Vocabulary</a>
 */
public final class Security
{
	private Security()
	{
		throw new AssertionError();
	}
	
	public static final String CONTEXT = "https://w3id.org/security/v1";
	
	public static final String BASE_IRI = "https://w3id.org/security#";
	
	/**
	 * Public key property.
	 * 
	 * @see <a href="https://w3c-ccg.github.io/security-vocab/#publicKey">publicKey</a>
	 */
	public record PublicKey(
		@JsonProperty(BASE_IRI + "id") URI id,
		@JsonProperty(BASE_IRI + "owner") URI owner,
		@JsonProperty(BASE_IRI + "publicKeyPem") String publicKeyPem
	)
	{
	}
}
