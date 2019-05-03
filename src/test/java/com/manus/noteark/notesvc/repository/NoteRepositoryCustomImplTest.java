package com.manus.noteark.notesvc.repository;

import static org.mockito.Mockito.when;

import java.util.Optional;

import com.manus.noteark.notesvc.AbstractTest;
import com.manus.noteark.notesvc.pojo.Note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;

public class NoteRepositoryCustomImplTest extends AbstractTest{

    @InjectMocks
    private NoteRepositoryCustomImpl noteRepositoryCustomImpl;
    @Mock
    private MongoTemplate mongoTemplateMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        noteRepositoryCustomImpl = new NoteRepositoryCustomImpl(mongoTemplateMock);
    }

    @Test
    public void findByNoteIdTest_noteFound_returnsOptionalOfNote() {
        String noteId = "1234";
        Note foundNote = testDataMaker.makeNote();

        when(mongoTemplateMock.findById(noteId, Note.class))
            .thenReturn(foundNote);

        assertEquals(Optional.of(foundNote), noteRepositoryCustomImpl.findByNoteId(noteId));
    }

    @Test
    public void findByNoteIdTest_noteNotFound_returnsOptionalEmpty() {
        String noteId = "1234";

        when(mongoTemplateMock.findById(noteId, Note.class))
            .thenReturn(null);

        assertEquals(Optional.empty(), noteRepositoryCustomImpl.findByNoteId(noteId));
    }

    @Test
    public void updateByNoteIdTest_noteExists_updatesNote() {
        String noteId = "1234";
        Note existingNote = testDataMaker.makeNote();
        existingNote.setId(noteId);
        Note updatedNote = testDataMaker.makeNote();
        updatedNote.setId(noteId);

        when(mongoTemplateMock.findById(noteId, Note.class))
            .thenReturn(existingNote);
        when(mongoTemplateMock.save(updatedNote))
            .thenReturn(updatedNote);

        assertEquals(Optional.of(updatedNote), noteRepositoryCustomImpl.updateByNoteId(noteId, updatedNote));
    }

    @Test
    public void updateByNoteIdTest_noteDoesNotExist_createsNewNote() {
        String noteId = "1234";
        Note updatedNote = testDataMaker.makeNote();
        Note newNote = testDataMaker.makeNote();
        newNote.setId("9876");

        when(mongoTemplateMock.findById(noteId, Note.class))
            .thenReturn(null);
        when(mongoTemplateMock.save(updatedNote))
            .thenReturn(newNote);

        assertEquals(Optional.of(newNote), noteRepositoryCustomImpl.updateByNoteId(noteId, updatedNote));
    }
}
