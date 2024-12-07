package org.hobsoft.raven.server;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jsonldjava.core.JsonLdConsts;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;

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
	public ResponseEntity<String> get(@PathVariable String username) throws IOException
	{
		// validate user
		var user = userRepository.findByName(username);
		if (user == null)
		{
			return ResponseEntity.notFound().build();
		}
		
		var actor = toActor(user);
		
		var objectMapper = new ObjectMapper();
		var content = objectMapper.writeValueAsString(actor);
		
		var jsonObject = JsonUtils.fromString(content);
		var context = Map.of(JsonLdConsts.CONTEXT, List.of(Activity.CONTEXT, Security.CONTEXT));
		var compact = JsonLdProcessor.compact(jsonObject, context, new JsonLdOptions());
		var compactContent = JsonUtils.toString(compact);
		
		return ResponseEntity.ok(compactContent);
	}
	
	private static Activity.Actor toActor(User user)
	{
		var actorId = MvcUriComponentsBuilder.fromController(ActorController.class).build(user.name());
		var type = Activity.Actor.PERSON_TYPE;
		var inboxUrl = MvcUriComponentsBuilder.fromController(InboxController.class).build(user.name());
		var outboxUrl = MvcUriComponentsBuilder.fromController(OutboxController.class).build(user.name());
		var publicKeyId = actorId.resolve("#main-key");
		var publicKey = new Security.PublicKey(publicKeyId, actorId, Keys.toPem(user.publicKey()));
		
		return new Activity.Actor(actorId, type, null, inboxUrl, outboxUrl, user.name(), publicKey);
	}
}
