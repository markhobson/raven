package org.hobsoft.raven.server;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ActivityPub actor.
 *
 * @see <a href="https://www.w3.org/TR/activitypub/#actor-objects">Actors</a>
 */
public record Actor(
	@JsonProperty("@context") List<URI> contexts,
	URI id,
	String type,
	URI inbox,
	String preferredUsername,
	Security.PublicKey publicKey
)
{
	public static final String PERSON_TYPE = "Person";
}
