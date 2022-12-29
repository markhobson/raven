package org.hobsoft.raven.server.it;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class HomeIT
{
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void canGetHomepage() throws Exception
	{
		mvc.perform(get("/")).andExpectAll(
			status().isOk(),
			content().contentType("text/html;charset=UTF-8"),
			content().xml("""
				<!DOCTYPE html>
				<html>
					<body>
						<h1>Raven</h1>
					</body>
				</html>
			""")
		);
	}
}
