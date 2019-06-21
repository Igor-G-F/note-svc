package com.manus.noteark.notesvc.service;

import com.manus.noteark.notesvc.AbstractTest;
import com.manus.noteark.notesvc.exception.NotFoundException;
import com.manus.noteark.notesvc.pojo.Note;
import com.manus.noteark.notesvc.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class NoteServiceTest extends AbstractTest{

    @InjectMocks
    private NoteService noteService;
    @Mock
    private NoteRepository noteRepositoryMock;

    public NoteServiceTest() {
        MockitoAnnotations.initMocks(this);
        noteService = new NoteService(noteRepositoryMock);
    }

    @Test
    public void createNoteTest_returnsCreatedNote() throws IOException {
        Note requestNote = getNote("new_note.json");
        Note createdNote = getNote("existing_note.json");

        when(noteRepositoryMock.save(requestNote))
            .thenReturn(createdNote);

        assertEquals(createdNote, noteService.createNote(requestNote));
    }

    @Test
    public void updateNoteWithIdTest_noteExists_returnsUpdatedNote() throws IOException {
        String updateTargetId = "1234";
        Note requestNote = getNote("new_note.json");
        Note updatedNote = getNote("new_note.json");
        updatedNote.setId("1234");

        when(noteRepositoryMock.updateByNoteId(updateTargetId, requestNote))
            .thenReturn(Optional.of(updatedNote));

        assertEquals(updatedNote, noteService.updateNoteWithId(updateTargetId, requestNote));
    }

    @Test
    public void updateNoteWithIdTest_noteDoesNotExist_returnsNewNote() throws IOException {
        String updateTargetId = "2345";
        Note requestNote = getNote("new_note.json");
        Note createdNote = getNote("existing_note.json");

        when(noteRepositoryMock.updateByNoteId(updateTargetId, requestNote))
            .thenReturn(Optional.of(createdNote));

        assertEquals(createdNote, noteService.updateNoteWithId(updateTargetId, requestNote));
    }

    @Test
    public void getNoteByIdTest_noteFound_returnsNote() throws IOException {
        Note existingNote = getNote("existing_note.json");

        when(noteRepositoryMock.findByNoteId(existingNote.getId()))
            .thenReturn(Optional.of(existingNote));

        assertEquals(existingNote, noteService.getNoteById(existingNote.getId()));
    }

    @Test
    public void getNoteByIdTest_noteNotFound_throwsException() {
        String noteId = "1234";

        when(noteRepositoryMock.findByNoteId(noteId))
            .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> noteService.getNoteById(noteId));
    }

    @Test
    public void getAllNotesForOwnerTest_hasNotes_returnsNotes() throws IOException {
        Note note = getNote("existing_note.json");
        List<Note> notes = new ArrayList<>();
        notes.add(note);
        notes.add(note);

        when(noteRepositoryMock.findByOwner(note.getOwner()))
            .thenReturn(notes);

        assertEquals(notes, noteService.getAllNotesForOwner(note.getOwner()));
    }

    @Test
    public void getAllNotesForOwnerTest_hasNoNotes_returnsEmptyList() {
        String noteOwner = "1234";
        List<Note> notes = new ArrayList<>();

        when(noteRepositoryMock.findByOwner(noteOwner))
            .thenReturn(notes);

        assertEquals(notes, noteService.getAllNotesForOwner(noteOwner));
    }

    @Test
    public void deleteNoteWithIdTest_noteFound_deletesNote() throws IOException {
        Note existingNote = getNote("existing_note.json");

        when(noteRepositoryMock.findByNoteId(existingNote.getId()))
                .thenReturn(Optional.of(existingNote));

        noteService.deleteNoteWithId(existingNote.getId());

        verify(noteRepositoryMock, times(1)).deleteById(existingNote.getId());
    }

    @Test
    public void deleteNoteWithIdTest_noteNotFound_throwsException() {
        String noteId = "1234";

        when(noteRepositoryMock.findByNoteId(noteId))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> noteService.deleteNoteWithId(noteId));
    }
}
