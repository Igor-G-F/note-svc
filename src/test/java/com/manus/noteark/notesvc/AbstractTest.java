package com.manus.noteark.notesvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manus.noteark.notesvc.pojo.Note;

import java.io.File;
import java.io.IOException;

public class AbstractTest {

    private static final String NOTE_FILE_PATH = "src/test/resources/notes/";

    protected Note getNote(String file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(NOTE_FILE_PATH+file), Note.class);
    }
}
