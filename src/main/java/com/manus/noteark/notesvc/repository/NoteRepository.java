package com.manus.noteark.notesvc.repository;

import java.util.List;
import java.util.Optional;

import com.manus.noteark.notesvc.pojo.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

    public Optional<Note> findById(String id);
    public List<Note> findByOwner(String owner);

}