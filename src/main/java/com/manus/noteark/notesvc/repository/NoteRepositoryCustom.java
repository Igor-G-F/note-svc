package com.manus.noteark.notesvc.repository;

import java.util.Optional;

import com.manus.noteark.notesvc.pojo.Note;

public interface NoteRepositoryCustom {

    public Optional<Note> findById(String id);

    public Optional<Note> updateById(String id, Note note);

}