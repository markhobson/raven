package org.hobsoft.raven.server.it;

import java.security.KeyPair;
import java.util.Base64;

import org.hobsoft.raven.server.TestKeys;
import org.hobsoft.raven.server.User;
import org.hobsoft.raven.server.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class ActorIT
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
	public void canGetActor() throws Exception
	{
		var publicKey = TestKeys.publicKey("alg", "fmt", Base64.getDecoder().decode("ABCD"));
		var keyPair = new KeyPair(publicKey, TestKeys.privateKey("alg", "fmt", new byte[0]));
		userRepository.save(new User("alice", keyPair));
		
		mvc.perform(get("/alice").header("X-Forwarded-Host", "social.example")).andExpectAll(
			status().isOk(),
			content().contentType("application/activity+json"),
			content().json("""
				{
					"@context": [
						"https://www.w3.org/ns/activitystreams",
						"https://w3id.org/security/v1"
					],
					"id": "http://social.example/alice",
					"type": "Person",
					"inbox": "http://social.example/alice/inbox",
					"outbox": "http://social.example/alice/outbox",
					"preferredUsername": "alice",
					"publicKey": {
						"id": "http://social.example/alice#main-key",
						"owner": "http://social.example/alice",
						"publicKeyPem": "-----BEGIN PUBLIC KEY-----\\nABCD\\n-----END PUBLIC KEY-----\\n"
					}
				}
			""", true)
		);
	}

	@Test
	public void cannotGetActorForUnknownUser() throws Exception
	{
		mvc.perform(get("/alice")).andExpect(status().isNotFound());
	}
}
