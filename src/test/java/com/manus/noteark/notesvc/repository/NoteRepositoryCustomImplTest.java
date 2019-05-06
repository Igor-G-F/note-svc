package com.manus.noteark.notesvc.repository;

import com.manus.noteark.notesvc.AbstractTest;
import com.manus.noteark.notesvc.pojo.Note;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NoteRepositoryCustomImplTest extends AbstractTest {

    @InjectMocks
    private NoteRepositoryCustomImpl noteRepositoryCustomImpl;
    @Mock
    private MongoTemplate mongoTemplateMock;

    public NoteRepositoryCustomImplTest() {
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
        Note requestNote = testDataMaker.makeNote();
        Note existingNote = testDataMaker.makeNote();
        existingNote.setId(noteId);
        Note updatedNote = testDataMaker.makeNote();
        updatedNote.setId(noteId);

        when(mongoTemplateMock.findById(noteId, Note.class))
                .thenReturn(existingNote);
        when(mongoTemplateMock.save(any(Note.class)))
                .thenReturn(updatedNote);

        ArgumentCaptor savedNoteCaptor = ArgumentCaptor.forClass(Note.class);
        Optional<Note> returnedNote = noteRepositoryCustomImpl.updateByNoteId(noteId, requestNote);

        assertEquals(Optional.of(updatedNote), returnedNote);
        verify(mongoTemplateMock, times(1)).save(savedNoteCaptor.capture());
        Note savedNote = (Note) savedNoteCaptor.getValue();
        assertEquals(updatedNote.getId(), savedNote.getId());
        assertEquals(updatedNote.getOwner(), savedNote.getOwner());
        assertEquals(updatedNote.getContent(), savedNote.getContent());
        assertEquals(updatedNote.getTitle(), savedNote.getTitle());
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
