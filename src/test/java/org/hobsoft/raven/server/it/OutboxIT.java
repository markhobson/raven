package org.hobsoft.raven.server.it;

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
	private MockMvc mvc;
	
	@Test
	public void canGetOutbox() throws Exception
	{
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
}
