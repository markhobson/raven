package org.hobsoft.raven.server;

import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long>
{
	User findByName(String name);
	
	User save(User user);
	
	void deleteAll();
}
