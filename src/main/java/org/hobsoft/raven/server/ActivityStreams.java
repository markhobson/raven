package org.hobsoft.raven.server;

import java.net.URI;

public final class ActivityStreams
{
	public static final String MIME_TYPE = "application/activity+json";
	
	public static final URI CONTEXT = URI.create("https://www.w3.org/ns/activitystreams");
	
	private ActivityStreams()
	{
		throw new AssertionError();
	}
}
