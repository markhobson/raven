package org.hobsoft.raven.server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequestMapping("{username}/outbox")
public class OutboxController
{
	private final UserRepository userRepository;
	
	public OutboxController(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}
	
	@GetMapping(produces = Activity.MIME_TYPE)
	public ResponseEntity<Activity.OrderedCollection> get(@PathVariable String username)
	{
		// validate user
		var user = userRepository.findByName(username);
		if (user == null)
		{
			return ResponseEntity.notFound().build();
		}
		
		var actorUrl = MvcUriComponentsBuilder.fromController(ActorController.class).build(user.name());
		var activities = user.notes().stream()
			.map(note -> Activity.Note.of(note.content()))
			.map(note -> Activity.Create.of(actorUrl, note))
			.toList();
		
		return ResponseEntity.ok(Activity.OrderedCollection.of(activities));
	}
}
