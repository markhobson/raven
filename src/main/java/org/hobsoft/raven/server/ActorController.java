package org.hobsoft.raven.server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.controller;

@RestController
@RequestMapping("{username}")
public class ActorController
{
	private final UserRepository userRepository;
	
	public ActorController(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}
	
	@GetMapping(produces = ActivityStreams.MIME_TYPE)
	public ResponseEntity<Actor> get(@PathVariable String username)
	{
		// validate user
		var user = userRepository.findByName(username);
		if (user == null)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(toActor(user));
	}
	
	private static Actor toActor(User user)
	{
		var id = MvcUriComponentsBuilder.fromMethodCall(controller(ActorController.class).get(user.name()))
			.build()
			.toUri();
		
		return new Actor(ActivityStreams.CONTEXT, id, Actor.PERSON_TYPE, user.name());
	}
}
