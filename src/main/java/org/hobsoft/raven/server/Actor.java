package org.hobsoft.raven.server;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ActivityPub actor.
 *
 * @see <a href="https://www.w3.org/TR/activitypub/#actor-objects">Actors</a>
 */
public record Actor(@JsonProperty("@context") URI context, URI id, String type, String preferredUsername)
{
	public static final String PERSON_TYPE = "Person";
}
