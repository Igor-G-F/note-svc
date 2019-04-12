package com.manus.noteark.notesvc.service;

import java.util.List;
import java.util.Optional;

import com.manus.noteark.notesvc.exception.NotFoundException;
import com.manus.noteark.notesvc.pojo.Note;
import com.manus.noteark.notesvc.repository.NoteRepository;

import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    public Note updateNoteWithId(String noteId, Note note) {
        return noteRepository.updateByNoteId(noteId, note).get();
    }

    public Note getNoteById(String noteId) {      
        Optional<Note> result = noteRepository.findByNoteId(noteId);
        if(!result.isPresent()) {
            throw new NotFoundException(String.format("\"note\" with id \"%s\"", noteId)); 
        } 
        return result.get();
    }

    public List<Note> getAllNotesForOwner(String owner) {
        return noteRepository.findByOwner(owner);
    }

}
