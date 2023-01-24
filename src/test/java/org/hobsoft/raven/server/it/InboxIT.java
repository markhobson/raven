package org.hobsoft.raven.server.it;

import org.hobsoft.raven.server.TestKeys;
import org.hobsoft.raven.server.User;
import org.hobsoft.raven.server.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class InboxIT
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MockMvc mvc;
	
	@AfterEach
	public void tearDown()
	{
		userRepository.deleteAll();
	}
	
	@Test
	public void cannotGetInbox() throws Exception
	{
		userRepository.save(new User(null, "alice", TestKeys.publicKey(), TestKeys.privateKey()));
		
		mvc.perform(get("/alice/inbox")).andExpect(status().isNotFound());
	}
}
