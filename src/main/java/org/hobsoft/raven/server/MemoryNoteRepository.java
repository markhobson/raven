package org.hobsoft.raven.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class MemoryNoteRepository implements NoteRepository
{
	private final Map<String, List<Note>> notes;
	
	public MemoryNoteRepository()
	{
		notes = new HashMap<>();
	}
	
	@Override
	public List<Note> findByUsername(String username)
	{
		return notes.getOrDefault(username, Collections.emptyList());
	}
	
	@Override
	public void save(String username, Note note)
	{
		notes.computeIfAbsent(username, key -> new ArrayList<>()).add(note);
	}
	
	@Override
	public void deleteAll()
	{
		notes.clear();
	}
}
