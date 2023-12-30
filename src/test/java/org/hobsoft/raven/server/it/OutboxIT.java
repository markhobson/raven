package org.hobsoft.raven.server.it;

import org.hobsoft.raven.server.Note;
import org.hobsoft.raven.server.NoteRepository;
import org.hobsoft.raven.server.User;
import org.hobsoft.raven.server.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class OutboxIT
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private MockMvc mvc;
	
	@AfterEach
	public void tearDown()
	{
		noteRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	public void canGetEmptyOutbox() throws Exception
	{
		userRepository.save(new User("alice", null));
		
		mvc.perform(get("/alice/outbox")).andExpectAll(
			status().isOk(),
			content().contentType("application/activity+json"),
			content().json("""
				{
					"@context": "https://www.w3.org/ns/activitystreams",
					"type": "OrderedCollection",
					"totalItems": 0,
					"orderedItems": []
				}
			""", true)
		);
	}
	
	@Test
	public void canGetOutboxActivity() throws Exception
	{
		userRepository.save(new User("alice", null));
		noteRepository.save("alice", new Note("Hello world"));
		
		mvc.perform(get("/alice/outbox").header("X-Forwarded-Host", "social.example")).andExpectAll(
			status().isOk(),
			content().contentType("application/activity+json"),
			content().json("""
				{
					"@context": "https://www.w3.org/ns/activitystreams",
					"type": "OrderedCollection",
					"totalItems": 1,
					"orderedItems": [
						{
							"type": "Create",
							"actor": "http://social.example/alice",
							"object": {
								"type": "Note",
								"content": "Hello world"
							}
						}
					]
				}
			""", true)
		);
	}
	
	@Test
	public void cannotGetOutboxForUnknownUser() throws Exception
	{
		mvc.perform(get("/alice/outbox")).andExpect(status().isNotFound());
	}
}
