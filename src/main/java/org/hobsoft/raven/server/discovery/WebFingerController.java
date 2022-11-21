package org.hobsoft.raven.server.discovery;

import java.net.URI;

import org.hobsoft.raven.server.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(".well-known/webfinger")
public class WebFingerController
{
	private static final String ACCT_SCHEME = "acct";
	
	private static final String APPLICATION_JRD_JSON_VALUE = "application/jrd+json";
	
	private final UserRepository userRepository;
	
	public WebFingerController(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}
	
	@GetMapping(produces = APPLICATION_JRD_JSON_VALUE)
	public ResponseEntity<ResourceDescriptor> discover(URI resource)
	{
		// validate scheme
		if (!ACCT_SCHEME.equals(resource.getScheme()))
		{
			return ResponseEntity.notFound().build();
		}
		
		// validate account
		Account account;
		try
		{
			account = Account.parse(resource.getSchemeSpecificPart());
		}
		catch (IllegalArgumentException exception)
		{
			return ResponseEntity.badRequest().build();
		}
		
		// validate host
		var host = ServletUriComponentsBuilder.fromCurrentServletMapping().build().getHost();
		if (!account.host().equals(host))
		{
			return ResponseEntity.notFound().build();
		}
		
		// validate user
		var user = userRepository.findByName(account.user());
		if (user == null)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(new ResourceDescriptor(resource));
	}
}
