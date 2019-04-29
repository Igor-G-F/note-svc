package com.manus.noteark.notesvc.helper;

import com.manus.noteark.notesvc.pojo.Note;

public class TestDataMaker {

    public Note makeNote() {
        Note result = new Note();
        result.setId(null);
        result.setOwner("5678");
        result.setTitle("Test note title");
        result.setContent("Test note content");
        return result;
    }

}
