package org.hobsoft.raven.server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class MemoryNoteRepository implements NoteRepository
{
	private final List<Note> notes;
	
	public MemoryNoteRepository()
	{
		notes = new ArrayList<>();
	}
	
	@Override
	public List<Note> findByUsername(String username)
	{
		return notes.stream().filter(note -> note.username().equals(username)).toList();
	}
	
	@Override
	public void save(Note note)
	{
		notes.add(note);
	}
	
	@Override
	public void deleteAll()
	{
		notes.clear();
	}
}
