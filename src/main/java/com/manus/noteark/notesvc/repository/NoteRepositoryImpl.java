package com.manus.noteark.notesvc.repository;

import java.util.Objects;
import java.util.Optional;

import com.manus.noteark.notesvc.pojo.Note;

import org.springframework.data.mongodb.core.MongoTemplate;

public class NoteRepositoryImpl implements NoteRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public NoteRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Note> findById(String id) {
        Note result = mongoTemplate.findById(id, Note.class);
        if(Objects.isNull(result)) {
            return Optional.empty();
        } 
        return Optional.of(result);
    }

    @Override
    public Optional<Note> updateById(String id, Note note) {
        if (findById(id).isPresent()) {
            note.setId(id);
            return Optional.of(mongoTemplate.save(note));
        } else {
            return Optional.of(mongoTemplate.save(note));
        }
    }

}