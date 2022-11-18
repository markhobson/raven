package org.hobsoft.raven.webfinger;

import java.net.URI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebFingerController
{
	private static final String APPLICATION_JRD_JSON_VALUE = "application/jrd+json";
	
	@GetMapping(value = ".well-known/webfinger", produces = APPLICATION_JRD_JSON_VALUE)
	public ResourceDescriptor discover(URI resource)
	{
		return new ResourceDescriptor(resource);
	}
}
