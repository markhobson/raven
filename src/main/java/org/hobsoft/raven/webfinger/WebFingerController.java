package org.hobsoft.raven.webfinger;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
		
		Account account;
		try
		{
			account = Account.parse(resource.getSchemeSpecificPart());
		}
		catch (IllegalArgumentException exception)
		{
			return ResponseEntity.badRequest().build();
		}
		
		String host = ServletUriComponentsBuilder.fromCurrentServletMapping().build().getHost();
		if (!account.host().equals(host))
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(new ResourceDescriptor(resource));
	}
}
