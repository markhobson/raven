package org.hobsoft.raven.webfinger;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebFingerController
{
	private static final String ACCT_SCHEME = "acct";
	
	private static final String APPLICATION_JRD_JSON_VALUE = "application/jrd+json";
	
	@GetMapping(value = ".well-known/webfinger", produces = APPLICATION_JRD_JSON_VALUE)
	public ResponseEntity<ResourceDescriptor> discover(URI resource)
	{
		if (!ACCT_SCHEME.equals(resource.getScheme()))
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(new ResourceDescriptor(resource));
	}
}
