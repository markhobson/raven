package org.hobsoft.raven.server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("{user}")
public class ActorController
{
	@GetMapping
	public ResponseEntity<Object> get(@PathVariable String user)
	{
		return ResponseEntity.noContent().build();
	}
}
