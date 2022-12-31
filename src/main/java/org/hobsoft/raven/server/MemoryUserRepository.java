package org.hobsoft.raven.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class MemoryUserRepository implements UserRepository
{
	private final Map<String, User> users;
	
	public MemoryUserRepository()
	{
		users = new HashMap<>();
	}
	
	@Override
	public User findByName(String name)
	{
		return users.get(name);
	}
	
	@Override
	public void save(User user)
	{
		users.put(user.name(), user);
	}
	
	@Override
	public void deleteAll()
	{
		users.clear();
	}
}
