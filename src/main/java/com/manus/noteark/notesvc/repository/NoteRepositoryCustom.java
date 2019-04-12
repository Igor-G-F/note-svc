package com.manus.noteark.notesvc.repository;

import java.util.Optional;

import com.manus.noteark.notesvc.pojo.Note;

public interface NoteRepositoryCustom {

    public Optional<Note> findByNoteId(String id);

    public Optional<Note> updateByNoteId(String id, Note note);

}