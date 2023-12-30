package org.hobsoft.raven.server;

import java.util.List;

public interface NoteRepository
{
	List<Note> findByUsername(String username);
	
	void save(String username, Note note);
	
	void deleteAll();
}
