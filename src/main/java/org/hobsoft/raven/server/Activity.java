package org.hobsoft.raven.server;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Activity Vocabulary for linked data.
 *
 * @see <a href="https://www.w3.org/TR/activitystreams-vocabulary/">Activity Vocabulary</a>
 */
public final class Activity
{
	public static final String MIME_TYPE = "application/activity+json";
	
	public static final URI CONTEXT = URI.create("https://www.w3.org/ns/activitystreams");
	
	private Activity()
	{
		throw new AssertionError();
	}
	
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
		URI outbox,
		String preferredUsername,
		Security.PublicKey publicKey
	)
	{
		public static final String PERSON_TYPE = "Person";
	}
	
	/**
	 * ActivityStreams ordered collection.
	 *
	 * @see <a href="https://www.w3.org/TR/activitystreams-vocabulary/#dfn-orderedcollection">OrderedCollection</a>
	 */
	public record OrderedCollection(
		@JsonProperty("@context") URI context,
		String type,
		int totalItems,
		List<Object> orderedItems
	)
	{
		public static final String TYPE = "OrderedCollection";
		
		public static Activity.OrderedCollection of(List<Object> orderedItems)
		{
			return new Activity.OrderedCollection(CONTEXT, TYPE, orderedItems.size(), orderedItems);
		}
	}
}
