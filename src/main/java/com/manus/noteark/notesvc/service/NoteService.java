package com.manus.noteark.notesvc.service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public Note updateNote(String noteId, Note newNote) {
        newNote.setId(noteId);
        return noteRepository.save(newNote);
    }

    public Note getNoteById(String noteId) {
        Note note; 
        try {
            note = noteRepository.findById(noteId).get();
        } catch(NoSuchElementException e) {
            throw new NotFoundException(String.format("note with id \"%s\"", noteId));
        }
        return note;
    }

    public List<Note> getAllNotesForOwner(String owner) {
        return noteRepository.findByOwner(owner);
    }

}
