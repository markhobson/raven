package org.hobsoft.raven.server;

import org.springframework.stereotype.Repository;

@Repository
public class DemoUserRepository implements UserRepository
{
	@Override
	public User findByName(String name)
	{
		if ("mark".equals(name))
		{
			return new User("mark");
		}
		
		return null;
	}
}
