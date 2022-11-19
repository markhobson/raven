package org.hobsoft.raven.it;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class DiscoverUserIT
{
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void canDiscoverUser() throws Exception
	{
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
}
