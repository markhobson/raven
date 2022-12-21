package org.hobsoft.raven.server.it;

import java.util.Collections;
import java.util.List;

import org.hobsoft.raven.server.Note;
import org.hobsoft.raven.server.User;
import org.hobsoft.raven.server.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class OutboxIT
{
	@MockBean
	private UserRepository userRepository;
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void canGetEmptyOutbox() throws Exception
	{
		when(userRepository.findByName("alice")).thenReturn(new User("alice", null, Collections.emptyList()));
		
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
		when(userRepository.findByName("alice")).thenReturn(new User("alice", null, List.of(new Note("Hello world"))));
		
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
							"@context": "https://www.w3.org/ns/activitystreams",
							"type": "Create",
							"actor": "http://social.example/alice",
							"object": {
								"@context": "https://www.w3.org/ns/activitystreams",
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
