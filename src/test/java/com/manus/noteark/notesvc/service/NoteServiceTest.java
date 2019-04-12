package com.manus.noteark.notesvc.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.manus.noteark.notesvc.AbstractTest;
import com.manus.noteark.notesvc.exception.NotFoundException;
import com.manus.noteark.notesvc.pojo.Note;
import com.manus.noteark.notesvc.repository.NoteRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class NoteServiceTest extends AbstractTest{

    @InjectMocks
    private NoteService noteService;
    @Mock
    private NoteRepository noteRepositoryMock;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before 
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        noteService = new NoteService(noteRepositoryMock);
    }

    @Test
    public void createNoteTest_returnsCreatedNote() {
        Note requestNote = makeNote();
        Note createdNote = makeNote();
        createdNote.setId("1234");

        when(noteRepositoryMock.save(requestNote))
            .thenReturn(createdNote);

        assertEquals(createdNote, noteService.createNote(requestNote));
    }

    @Test
    public void updateNoteWithIdTest_noteExists_returnsUpdatedNote() {
        String updateTargetId = "1234";
        Note requestNote = makeNote();
        Note updatedNote = makeNote();
        updatedNote.setId("1234");

        when(noteRepositoryMock.updateByNoteId(updateTargetId, requestNote))
            .thenReturn(Optional.of(updatedNote));

        assertEquals(updatedNote, noteService.updateNoteWithId(updateTargetId, requestNote));
    }

    @Test
    public void updateNoteWithIdTest_noteDoesNotExist_returnsNewNote() {
        String updateTargetId = "2345";
        Note requestNote = makeNote();
        Note updatedNote = makeNote();
        updatedNote.setId("1234");

        when(noteRepositoryMock.updateByNoteId(updateTargetId, requestNote))
            .thenReturn(Optional.of(updatedNote));

        assertEquals(updatedNote, noteService.updateNoteWithId(updateTargetId, requestNote));
    }

    @Test
    public void getNoteByIdTest_noteFound_returnsNote() {      
        String noteId = "1234";
        Note foundNote = makeNote();
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

        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage("Could not find \"note\" with id \"1234\"");

        noteService.getNoteById(noteId);
    }

    @Test
    public void getAllNotesForOwnerTest_hasNotes_returnsNotes() {
        String noteOwner = "1234";
        List<Note> notes = new ArrayList<>();
        notes.add(makeNote());
        notes.add(makeNote());

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
}
