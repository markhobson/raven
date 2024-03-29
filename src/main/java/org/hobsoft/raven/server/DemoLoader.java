package org.hobsoft.raven.server;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("demo")
public class DemoLoader
{
	private static final Logger LOG = LoggerFactory.getLogger(DemoLoader.class);

	public DemoLoader(UserRepository userRepository, NoteRepository noteRepository)
	{
		var keyPair = generateKeyPair();
		var user = userRepository.save(new User(null, "mark", keyPair.getPublic(), keyPair.getPrivate()));
		noteRepository.save(new Note(null, user.id(), "Up early and feel great!"));
	}
	
	private static KeyPair generateKeyPair()
	{
		LOG.info("Generating key pair");
		
		try
		{
			var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(4096);
			return keyPairGenerator.generateKeyPair();
		}
		catch (NoSuchAlgorithmException exception)
		{
			throw new IllegalStateException(exception);
		}
	}
}
