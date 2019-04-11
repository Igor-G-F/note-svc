package com.manus.noteark.notesvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.manus.noteark.notesvc.pojo.Note;
import com.manus.noteark.notesvc.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PutMapping("/note/{noteId}")
    public Note updateNoteWithId(@PathVariable String noteId, @RequestBody Note note, HttpServletResponse response) {
        Note result = noteService.updateNoteWithId(noteId, note);
        if (!result.getId().equals(noteId)) response.setStatus(HttpServletResponse.SC_CREATED);
        return result;
    }

    @GetMapping("/note/{noteId}")
    public Note getNoteById(@PathVariable String noteId) {
        return noteService.getNoteById(noteId);
    }

    @GetMapping("/notes/{owner}")
    public List<Note> getAllNotesForOwner(@PathVariable String owner) {
        return noteService.getAllNotesForOwner(owner);
    }


}
