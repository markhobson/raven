package org.hobsoft.raven.server;

import java.net.URI;
import java.util.List;

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
	
	public static final String CONTEXT = "https://www.w3.org/ns/activitystreams";
	
	public static final String BASE_IRI = "https://www.w3.org/ns/activitystreams#";
	
	private Activity()
	{
		throw new AssertionError();
	}
	
	/**
	 * ActivityStreams link.
	 *
	 * @see <a href="https://www.w3.org/TR/activitystreams-vocabulary/#dfn-link">Link</a>
	 */
	public record Link(@JsonProperty("@id") URI id)
	{
		public static Link of(URI id)
		{
			return new Link(id);
		}
	}
	
	/**
	 * ActivityStreams object.
	 *
	 * @see <a href="https://www.w3.org/TR/activitystreams-vocabulary/#dfn-object">Object</a>
	 */
	public interface AbstractObject
	{
		String TYPE = "Object";
		
		@JsonProperty(BASE_IRI + "type")
		String type();
		
		@JsonProperty(BASE_IRI + "content")
		@JsonInclude(JsonInclude.Include.NON_NULL)
		String content();
	}
	
	/**
	 * ActivityPub actor.
	 *
	 * @see <a href="https://www.w3.org/TR/activitypub/#actor-objects">Actors</a>
	 */
	public record Actor(
		@JsonProperty(BASE_IRI + "id") URI id,
		String type,
		String content,
		@JsonProperty(BASE_IRI + "inbox") URI inbox,
		@JsonProperty(BASE_IRI + "outbox") URI outbox,
		@JsonProperty(BASE_IRI + "preferredUsername") String preferredUsername,
		@JsonProperty(Security.BASE_IRI + "publicKey") Security.PublicKey publicKey
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
		String type,
		String content,
		@JsonProperty(BASE_IRI + "totalItems") int totalItems,
		@JsonProperty(BASE_IRI + "orderedItems") List<? extends AbstractObject> orderedItems
	) implements AbstractObject
	{
		public static final String TYPE = "OrderedCollection";
		
		public static OrderedCollection of(List<? extends AbstractObject> orderedItems)
		{
			return new OrderedCollection(TYPE, null, orderedItems.size(), orderedItems);
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
		
		@JsonProperty(BASE_IRI + "actor")
		Link actor();
		
		@JsonProperty(BASE_IRI + "object")
		AbstractObject object();
	}
	
	/**
	 * ActivityStreams create activity.
	 *
	 * @see <a href="https://www.w3.org/TR/activitystreams-vocabulary/#dfn-create">Create</a>
	 */
	public record Create(
		String type,
		String content,
		Link actor,
		AbstractObject object
	) implements AbstractActivity
	{
		public static final String TYPE = "Create";
		
		public static Create of(URI actor, AbstractObject object)
		{
			return new Create(TYPE, null, Link.of(actor), object);
		}
	}
	
	public record Note(
		String type,
		String content
	) implements AbstractObject
	{
		public static final String TYPE = "Note";
		
		public static Note of(String content)
		{
			return new Note(TYPE, content);
		}
	}
}
