package com.manus.noteark.notesvc.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.manus.noteark.notesvc.pojo.Note;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class OwnerControllerIT extends AbstractIT {

  @Test
  public void getAllNotesForOwnerTest_ownerExists_returns200andCreatedNote() throws Exception {
    Note existingNote = testDataMaker.makeNote();

    noteRepository.save(existingNote);
    noteRepository.save(existingNote);

    MvcResult result = mockMvc.perform(get(String.format("/owner/%s/notes", existingNote.getOwner())))
            .andExpect(status().isOk())
            .andReturn();

    List<Note> responseBody = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Note>>(){});
    assertNotNull(responseBody);

    responseBody.forEach(n -> assertEquals(existingNote.getOwner(), n.getOwner()));

  }

}
