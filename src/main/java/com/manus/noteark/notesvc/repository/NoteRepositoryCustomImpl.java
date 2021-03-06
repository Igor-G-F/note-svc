package com.manus.noteark.notesvc.repository;

import java.util.Objects;
import java.util.Optional;

import com.manus.noteark.notesvc.pojo.Note;

import org.springframework.data.mongodb.core.MongoTemplate;

public class NoteRepositoryCustomImpl implements NoteRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public NoteRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Note> findByNoteId(String id) {
        Note result = mongoTemplate.findById(id, Note.class);
        if(Objects.isNull(result)) {
            return Optional.empty();
        }
        return Optional.of(result);
    }

    @Override
    public Optional<Note> updateByNoteId(String id, Note note) {
        if (findByNoteId(id).isPresent()) {
            note.setId(id);
            return Optional.of(mongoTemplate.save(note));
        } else {
            return Optional.of(mongoTemplate.save(note));
        }
    }

}
