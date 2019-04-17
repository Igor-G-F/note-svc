package com.manus.noteark.notesvc.helper;

import com.manus.noteark.notesvc.pojo.Note;
import com.manus.noteark.notesvc.pojo.NoteRequest;

public class TestDataMaker {

    public Note makeNote() {
        Note result = new Note();
        result.setId(null);
        result.setOwner("5678");
        result.setTitle("Test note title");
        result.setContent("Test note content");
        return result;
    }

    public NoteRequest makeNoteRequest() {
        NoteRequest result = new NoteRequest();
        result.setOwner("5678");
        result.setTitle("Test note request title");
        result.setContent("Test note request content");
        return result;
    }

}
