package com.manus.noteark.notesvc.repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.manus.noteark.notesvc.AbstractTest;
import com.manus.noteark.notesvc.pojo.Note;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class NoteRepositoryImplTest extends AbstractTest{

    @InjectMocks 
    private NoteRepositoryImpl noteRepositoryImpl;
    @Mock
    private MongoTemplate mongoTemplateMock;

    @Before 
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        noteRepositoryImpl = new NoteRepositoryImpl(mongoTemplateMock);
    }

    @Test
    public void findByNoteIdTest_noteFound_returnsOptionalOfNote() {
        String noteId = "1234";
        Note foundNote = makeNote();

        when(mongoTemplateMock.findById(noteId, Note.class))
            .thenReturn(foundNote);

        assertEquals(Optional.of(foundNote), noteRepositoryImpl.findByNoteId(noteId));
    }    

    @Test
    public void findByNoteIdTest_noteNotFound_returnsOptionalEmpty() {
        String noteId = "1234";

        when(mongoTemplateMock.findById(noteId, Note.class))
            .thenReturn(null);

        assertEquals(Optional.empty(), noteRepositoryImpl.findByNoteId(noteId));
    }   

    @Test
    public void updateByNoteIdTest_noteExists_updatesNote() {
        String noteId = "1234";
        Note existingNote = makeNote();
        existingNote.setId(noteId);
        Note updatedNote = makeNote();
        updatedNote.setId(noteId);

        when(mongoTemplateMock.findById(noteId, Note.class))
            .thenReturn(existingNote);
        when(mongoTemplateMock.save(updatedNote))
            .thenReturn(updatedNote);

        assertEquals(Optional.of(updatedNote), noteRepositoryImpl.updateByNoteId(noteId, updatedNote));
    }    

    @Test
    public void updateByNoteIdTest_noteDoesNotExist_createsNewNote() {
        String noteId = "1234";
        Note updatedNote = makeNote();
        Note newNote = makeNote();
        newNote.setId("9876");

        when(mongoTemplateMock.findById(noteId, Note.class))
            .thenReturn(null);
        when(mongoTemplateMock.save(updatedNote))
            .thenReturn(newNote);

        assertEquals(Optional.of(newNote), noteRepositoryImpl.updateByNoteId(noteId, updatedNote));
    }    
}