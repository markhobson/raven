package org.hobsoft.raven.server.it;

import java.util.Collections;

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
public class DiscoverUserIT
{
	@MockBean
	private UserRepository userRepository;
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void canDiscoverUser() throws Exception
	{
		when(userRepository.findByName("alice")).thenReturn(new User("alice", null, Collections.emptyList()));
		
		mvc.perform(get("/.well-known/webfinger")
				.queryParam("resource", "acct:alice@social.example")
				.header("X-Forwarded-Host", "social.example")
			)
			.andExpectAll(
				status().isOk(),
				content().contentType("application/jrd+json"),
				content().json("""
					{
						"subject": "acct:alice@social.example",
						"links": [
							{
								"rel": "self",
								"type": "application/activity+json",
								"href": "http://social.example/alice"
							}
						]
					}
				""")
			);
	}
	
	@Test
	public void cannotDiscoverResourceForDifferentScheme() throws Exception
	{
		mvc.perform(get("/.well-known/webfinger").queryParam("resource", "mailto:alice@social.example"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void cannotDiscoverUserForMalformedAccount() throws Exception
	{
		mvc.perform(get("/.well-known/webfinger").queryParam("resource", "acct:alice"))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void cannotDiscoverUserForDifferentServer() throws Exception
	{
		mvc.perform(get("/.well-known/webfinger")
				.queryParam("resource", "acct:alice@chatty.example")
				.header("X-Forwarded-Host", "social.example")
			)
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void cannotDiscoverUserForUnknownUser() throws Exception
	{
		mvc.perform(get("/.well-known/webfinger")
				.queryParam("resource", "acct:alice@social.example")
				.header("X-Forwarded-Host", "social.example")
			)
			.andExpect(status().isNotFound());
	}
}
