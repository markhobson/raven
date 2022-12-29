package org.hobsoft.raven.server;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class DemoLoader
{
	public DemoLoader(UserRepository userRepository)
	{
		var user = new User("mark", generateKeyPair(), List.of(new Note("Up early and feel great!")));
		userRepository.save(user);
	}
	
	private static KeyPair generateKeyPair()
	{
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
