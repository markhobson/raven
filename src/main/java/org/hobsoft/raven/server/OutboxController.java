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
	
	private final NoteRepository noteRepository;
	
	public OutboxController(UserRepository userRepository, NoteRepository noteRepository)
	{
		this.userRepository = userRepository;
		this.noteRepository = noteRepository;
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
		var notes = noteRepository.findByUserId(user.id());
		var activities = notes.stream()
			.map(note -> Activity.Note.of(note.content()))
			.map(note -> Activity.Create.of(actorUrl, note))
			.toList();
		
		return ResponseEntity.ok(Activity.OrderedCollection.of(activities));
	}
}
