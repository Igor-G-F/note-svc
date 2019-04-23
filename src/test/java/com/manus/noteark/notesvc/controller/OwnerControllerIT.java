package com.manus.noteark.notesvc.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.manus.noteark.notesvc.pojo.Note;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
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
