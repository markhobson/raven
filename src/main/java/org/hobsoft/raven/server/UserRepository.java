package org.hobsoft.raven.server;

public interface UserRepository
{
	User findByName(String name);
}