package com.manus.noteark.notesvc.controller;

import java.util.List;

import com.manus.noteark.notesvc.pojo.Note;
import com.manus.noteark.notesvc.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/note")
    @ResponseStatus(HttpStatus.CREATED)
    public Note createNote(@RequestBody Note note) {
        return noteService.createNote(note);
    }

    @PutMapping("/note")
    public Note updateNoteWithId(@RequestParam String noteId, @RequestBody Note newNote) {
        return noteService.updateNote(noteId, newNote);
    }

    @GetMapping("/note")
    public Note getNoteById(@RequestParam String noteId) {
        return noteService.getNoteById(noteId);
    }

    @GetMapping("/notes")
    public List<Note> getAllNotesForOwner(@RequestParam String owner) {
        return noteService.getAllNotesForOwner(owner);
    }


}
