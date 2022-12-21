package org.hobsoft.raven.server;

import java.util.Collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("{username}/outbox")
public class OutboxController
{
	@GetMapping(produces = Activity.MIME_TYPE)
	public ResponseEntity<Activity.OrderedCollection> get(@PathVariable String username)
	{
		var orderedItems = Collections.emptyList();
		
		return ResponseEntity.ok(Activity.OrderedCollection.of(orderedItems));
	}
}
