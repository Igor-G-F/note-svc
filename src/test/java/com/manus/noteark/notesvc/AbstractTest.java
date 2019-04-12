package com.manus.noteark.notesvc;

import com.manus.noteark.notesvc.pojo.Note;

public class AbstractTest {

    public Note makeNote() {
        Note result = new Note();
        result.setId(null);
        result.setOwner("5678");
        result.setTitle("Test title");
        result.setContent("Test content");
        return result;
    }
    
}