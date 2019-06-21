package com.manus.noteark.notesvc.repository;

import com.manus.noteark.notesvc.AbstractTest;
import com.manus.noteark.notesvc.pojo.Note;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
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
    public void findByNoteIdTest_noteFound_returnsOptionalOfNote() throws IOException {
        Note foundNote = getNote("existing_note.json");

        when(mongoTemplateMock.findById(foundNote.getId(), Note.class))
                .thenReturn(foundNote);

        assertEquals(Optional.of(foundNote), noteRepositoryCustomImpl.findByNoteId(foundNote.getId()));
    }

    @Test
    public void findByNoteIdTest_noteNotFound_returnsOptionalEmpty() {
        String noteId = "1234";

        when(mongoTemplateMock.findById(noteId, Note.class))
                .thenReturn(null);

        assertEquals(Optional.empty(), noteRepositoryCustomImpl.findByNoteId(noteId));
    }

    @Test
    public void updateByNoteIdTest_noteExists_updatesNote() throws IOException {
        Note requestNote = getNote("new_note.json");
        requestNote.setContent("Hello There");
        Note existingNote = getNote("existing_note.json");
        Note updatedNote = getNote("new_note.json");
        updatedNote.setContent("Hello There");
        updatedNote.setId(existingNote.getId());

        when(mongoTemplateMock.findById(existingNote.getId(), Note.class))
                .thenReturn(existingNote);
        when(mongoTemplateMock.save(any(Note.class)))
                .thenReturn(updatedNote);

        ArgumentCaptor savedNoteCaptor = ArgumentCaptor.forClass(Note.class);
        Optional<Note> returnedNote = noteRepositoryCustomImpl.updateByNoteId(existingNote.getId(), requestNote);

        assertEquals(Optional.of(updatedNote), returnedNote);
        verify(mongoTemplateMock, times(1)).save(savedNoteCaptor.capture());
        Note savedNote = (Note) savedNoteCaptor.getValue();
        assertEquals(updatedNote.getId(), savedNote.getId());
        assertEquals(updatedNote.getOwner(), savedNote.getOwner());
        assertEquals(updatedNote.getContent(), savedNote.getContent());
        assertEquals(updatedNote.getTitle(), savedNote.getTitle());
    }

    @Test
    public void updateByNoteIdTest_noteDoesNotExist_createsNewNote() throws IOException {
        String noteId = "1234";
        Note updatedNote = getNote("new_note.json");
        Note newNote = getNote("new_note.json");
        newNote.setId("9876");

        when(mongoTemplateMock.findById(noteId, Note.class))
                .thenReturn(null);
        when(mongoTemplateMock.save(updatedNote))
                .thenReturn(newNote);

        assertEquals(Optional.of(newNote), noteRepositoryCustomImpl.updateByNoteId(noteId, updatedNote));
    }
}
