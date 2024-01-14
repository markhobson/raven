package org.hobsoft.raven.server;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface NoteRepository extends Repository<Note, Long>
{
	List<Note> findByUserId(Long userId);
	
	Note save(Note note);
	
	void deleteAll();
}
