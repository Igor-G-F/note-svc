package com.manus.noteark.notesvc.controller;

import com.manus.noteark.notesvc.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {

    private NoteService noteService;

    public NoteController() {
        noteService = new NoteService();
    }

    @PostMapping("/note")
    @ResponseStatus(HttpStatus.CREATED)
    public String createNote() {
        return noteService.createNote();
    }

    @GetMapping("/note")
    @ResponseStatus(HttpStatus.FOUND)
    public String getNote(@RequestParam(name = "id") String noteId) {
        return noteService.getNote(noteId);
    }


}
