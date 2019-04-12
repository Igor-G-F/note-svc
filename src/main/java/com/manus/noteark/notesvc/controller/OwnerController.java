package com.manus.noteark.notesvc.controller;

import java.util.List;

import com.manus.noteark.notesvc.pojo.Note;
import com.manus.noteark.notesvc.service.NoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/owner", produces={"application/json"})
public class OwnerController {

    private final NoteService noteService;

    public OwnerController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/{ownerId}/notes")
    public List<Note> getAllNotesForOwner(@PathVariable String ownerId) {
        return noteService.getAllNotesForOwner(ownerId);
    }

}
