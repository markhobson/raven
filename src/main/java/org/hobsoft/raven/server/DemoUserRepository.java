package org.hobsoft.raven.server;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class DemoUserRepository implements UserRepository
{
	private final Map<String, User> users;
	
	public DemoUserRepository()
	{
		users = Map.of("mark", new User("mark", generateKeyPair()));
	}
	
	@Override
	public User findByName(String name)
	{
		return users.get(name);
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
