package org.hobsoft.raven.server.it;

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
	public void canGetOutbox() throws Exception
	{
		when(userRepository.findByName("alice")).thenReturn(new User("alice", null));
		
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
			""")
		);
	}
	
	@Test
	public void cannotGetOutboxForUnknownUser() throws Exception
	{
		mvc.perform(get("/alice/outbox")).andExpect(status().isNotFound());
	}
}
