package org.hobsoft.raven.server;

import java.net.URI;

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
}
