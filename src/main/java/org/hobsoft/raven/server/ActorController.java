package org.hobsoft.raven.server;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequestMapping("{username}")
public class ActorController
{
	private final UserRepository userRepository;
	
	public ActorController(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}
	
	@GetMapping(produces = Activity.MIME_TYPE)
	public ResponseEntity<Activity.Actor> get(@PathVariable String username)
	{
		// validate user
		var user = userRepository.findByName(username);
		if (user == null)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(toActor(user));
	}
	
	private static Activity.Actor toActor(User user)
	{
		var contexts = List.of(Activity.CONTEXT, Security.CONTEXT);
		var actorId = MvcUriComponentsBuilder.fromController(ActorController.class).build(user.name());
		var type = Activity.Actor.PERSON_TYPE;
		var inboxUrl = MvcUriComponentsBuilder.fromController(InboxController.class).build(user.name());
		var outboxUrl = MvcUriComponentsBuilder.fromController(OutboxController.class).build(user.name());
		var publicKeyId = actorId.resolve("#main-key");
		var publicKey = new Security.PublicKey(publicKeyId, actorId, Keys.toPem(user.keyPair().getPublic()));
		
		return new Activity.Actor(contexts, actorId, type, null, inboxUrl, outboxUrl, user.name(), publicKey);
	}
}
