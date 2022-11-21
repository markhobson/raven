package org.hobsoft.raven.server.it;

import org.hobsoft.raven.server.User;
import org.hobsoft.raven.server.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class DiscoverUserIT
{
	@MockBean
	private UserRepository userRepository;
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void canDiscoverUser() throws Exception
	{
		when(userRepository.findByName("alice")).thenReturn(new User("alice"));
		
		mvc.perform(get("/.well-known/webfinger")
				.queryParam("resource", "acct:alice@alice.com")
				.header("X-Forwarded-Host", "alice.com")
			)
			.andExpectAll(
				status().isOk(),
				content().contentType("application/jrd+json"),
				content().json("""
					{
						"subject": "acct:alice@alice.com"
					}
				""")
			);
	}
	
	@Test
	public void cannotDiscoverResourceForDifferentScheme() throws Exception
	{
		mvc.perform(get("/.well-known/webfinger").queryParam("resource", "mailto:alice@alice.com"))
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
				.queryParam("resource", "acct:alice@bob.com")
				.header("X-Forwarded-Host", "alice.com")
			)
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void cannotDiscoverUserForUnknownUser() throws Exception
	{
		mvc.perform(get("/.well-known/webfinger")
				.queryParam("resource", "acct:alice@alice.com")
				.header("X-Forwarded-Host", "alice.com")
			)
			.andExpect(status().isNotFound());
	}
}
