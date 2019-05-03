package com.manus.noteark.notesvc.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.manus.noteark.notesvc.AbstractTest;
import com.manus.noteark.notesvc.exception.NotFoundException;
import com.manus.noteark.notesvc.pojo.Note;
import com.manus.noteark.notesvc.repository.NoteRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NoteServiceTest extends AbstractTest{

    @InjectMocks
    private NoteService noteService;
    @Mock
    private NoteRepository noteRepositoryMock;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        noteService = new NoteService(noteRepositoryMock);
    }

    @Test
    public void createNoteTest_returnsCreatedNote() {
        Note requestNote = testDataMaker.makeNote();
        Note createdNote = testDataMaker.makeNote();
        createdNote.setId("1234");

        when(noteRepositoryMock.save(requestNote))
            .thenReturn(createdNote);

        assertEquals(createdNote, noteService.createNote(requestNote));
    }

    @Test
    public void updateNoteWithIdTest_noteExists_returnsUpdatedNote() {
        String updateTargetId = "1234";
        Note requestNote = testDataMaker.makeNote();
        Note updatedNote = testDataMaker.makeNote();
        updatedNote.setId("1234");

        when(noteRepositoryMock.updateByNoteId(updateTargetId, requestNote))
            .thenReturn(Optional.of(updatedNote));

        assertEquals(updatedNote, noteService.updateNoteWithId(updateTargetId, requestNote));
    }

    @Test
    public void updateNoteWithIdTest_noteDoesNotExist_returnsNewNote() {
        String updateTargetId = "2345";
        Note requestNote = testDataMaker.makeNote();
        Note updatedNote = testDataMaker.makeNote();
        updatedNote.setId("1234");

        when(noteRepositoryMock.updateByNoteId(updateTargetId, requestNote))
            .thenReturn(Optional.of(updatedNote));

        assertEquals(updatedNote, noteService.updateNoteWithId(updateTargetId, requestNote));
    }

    @Test
    public void getNoteByIdTest_noteFound_returnsNote() {
        String noteId = "1234";
        Note foundNote = testDataMaker.makeNote();
        foundNote.setId(noteId);

        when(noteRepositoryMock.findByNoteId(noteId))
            .thenReturn(Optional.of(foundNote));

        assertEquals(foundNote, noteService.getNoteById(noteId));
    }

    @Test
    public void getNoteByIdTest_noteNotFound_throwsException() {
        String noteId = "1234";

        when(noteRepositoryMock.findByNoteId(noteId))
            .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            noteService.getNoteById(noteId);
        });
    }

    @Test
    public void getAllNotesForOwnerTest_hasNotes_returnsNotes() {
        String noteOwner = "1234";
        List<Note> notes = new ArrayList<>();
        notes.add(testDataMaker.makeNote());
        notes.add(testDataMaker.makeNote());

        when(noteRepositoryMock.findByOwner(noteOwner))
            .thenReturn(notes);

        assertEquals(notes, noteService.getAllNotesForOwner(noteOwner));
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
    public void deleteNoteWithIdTest_noteFound_deletesNote() {
        String noteId = "1234";
        Note foundNote = testDataMaker.makeNote();
        foundNote.setId(noteId);

        when(noteRepositoryMock.findByNoteId(noteId))
                .thenReturn(Optional.of(foundNote));

        noteService.deleteNoteWithId(noteId);

        verify(noteRepositoryMock, times(1)).deleteById(noteId);
    }

    @Test
    public void deleteNoteWithIdTest_noteNotFound_throwsException() {
        String noteId = "1234";

        when(noteRepositoryMock.findByNoteId(noteId))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            noteService.deleteNoteWithId(noteId);
        });
    }
}
