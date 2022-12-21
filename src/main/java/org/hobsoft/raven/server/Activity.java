package org.hobsoft.raven.server;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		List<URI> contexts();
		
		String type();
		
		@JsonInclude(JsonInclude.Include.NON_NULL)
		String content();
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
		String content,
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
		String content,
		int totalItems,
		List<? extends AbstractObject> orderedItems
	) implements AbstractObject
	{
		public static final String TYPE = "OrderedCollection";
		
		public static OrderedCollection of(List<? extends AbstractObject> orderedItems)
		{
			return new OrderedCollection(List.of(CONTEXT), TYPE, null, orderedItems.size(), orderedItems);
		}
	}
	
	/**
	 * ActivityStreams activity.
	 *
	 * @see <a href="https://www.w3.org/TR/activitystreams-vocabulary/#dfn-activity">Activity</a>
	 */
	public interface AbstractActivity extends AbstractObject
	{
		String TYPE = "Activity";
		
		URI actor();
		
		AbstractObject object();
	}
	
	/**
	 * ActivityStreams create activity.
	 *
	 * @see <a href="https://www.w3.org/TR/activitystreams-vocabulary/#dfn-create">Create</a>
	 */
	public record Create(
		List<URI> contexts,
		String type,
		String content,
		URI actor,
		AbstractObject object
	) implements AbstractActivity
	{
		public static final String TYPE = "Create";
		
		public static Create of(URI actor, AbstractObject object)
		{
			return new Create(Collections.emptyList(), TYPE, null, actor, object);
		}
	}
	
	public record Note(
		List<URI> contexts,
		String type,
		String content
	) implements AbstractObject
	{
		public static final String TYPE = "Note";
		
		public static Note of(String content)
		{
			return new Note(Collections.emptyList(), TYPE, content);
		}
	}
}
