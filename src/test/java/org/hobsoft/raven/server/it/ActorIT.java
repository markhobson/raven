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
public class ActorIT
{
	@MockBean
	private UserRepository userRepository;
	
	@Autowired
	private MockMvc mvc;

	@Test
	public void canGetActor() throws Exception
	{
		when(userRepository.findByName("alice")).thenReturn(new User("alice"));
		
		mvc.perform(get("/alice/").header("X-Forwarded-Host", "social.example")).andExpectAll(
			status().isOk(),
			content().contentType("application/activity+json"),
			content().json("""
				{
					"@context": "https://www.w3.org/ns/activitystreams",
					"id": "http://social.example/alice",
					"type": "Person",
					"inbox": "http://social.example/alice/inbox",
					"preferredUsername": "alice"
				}
			""")
		);
	}

	@Test
	public void cannotGetActorForUnknownUser() throws Exception
	{
		mvc.perform(get("/alice/")).andExpect(status().isNotFound());
	}
}
