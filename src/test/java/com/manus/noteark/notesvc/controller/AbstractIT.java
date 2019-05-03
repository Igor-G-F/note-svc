package com.manus.noteark.notesvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manus.noteark.notesvc.AbstractTest;
import com.manus.noteark.notesvc.NoteSvcApplication;
import com.manus.noteark.notesvc.repository.NoteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = NoteSvcApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("integrationTest")
public class AbstractIT extends AbstractTest {

  @Autowired
  public MockMvc mockMvc;

  @Autowired
  public ObjectMapper objectMapper;

  @Autowired
  public NoteRepository noteRepository;

  @BeforeEach
  public void clearNotes () {
    noteRepository.deleteAll();
  }

}
