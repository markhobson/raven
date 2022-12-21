package org.hobsoft.raven.server;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	 * ActivityStreams object.
	 *
	 * @see <a href="https://www.w3.org/TR/activitystreams-vocabulary/#dfn-object">Object</a>
	 */
	public interface AbstractObject
	{
		String TYPE = "Object";
		
		@JsonProperty("@context")
		@JsonFormat(with = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
		List<URI> contexts();
		
		String type();
	}
	
	/**
	 * ActivityPub actor.
	 *
	 * @see <a href="https://www.w3.org/TR/activitypub/#actor-objects">Actors</a>
	 */
	public record Actor(
		List<URI> contexts,
		URI id,
		String type,
		URI inbox,
		URI outbox,
		String preferredUsername,
		Security.PublicKey publicKey
	) implements AbstractObject
	{
		public static final String PERSON_TYPE = "Person";
	}
	
	/**
	 * ActivityStreams ordered collection.
	 *
	 * @see <a href="https://www.w3.org/TR/activitystreams-vocabulary/#dfn-orderedcollection">OrderedCollection</a>
	 */
	public record OrderedCollection(
		List<URI> contexts,
		String type,
		int totalItems,
		List<AbstractObject> orderedItems
	) implements AbstractObject
	{
		public static final String TYPE = "OrderedCollection";
		
		public static Activity.OrderedCollection of(List<AbstractObject> orderedItems)
		{
			return new Activity.OrderedCollection(List.of(CONTEXT), TYPE, orderedItems.size(), orderedItems);
		}
	}
}
