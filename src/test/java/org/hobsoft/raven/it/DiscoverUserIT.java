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
		mvc.perform(get("/.well-known/webfinger").queryParam("resource", "acct:alice")).andExpectAll(
			status().isOk(),
			content().contentType("application/jrd+json"),
			content().json("""
				{
					"subject": "acct:alice"
				}
			""")
		);
	}
}
