package com.manus.noteark.notesvc.controller;

import com.manus.noteark.notesvc.pojo.Note;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class NoteControllerIT extends AbstractIT {

  @Test
  public void createNoteTest_validRequest_returns201andCreatedNote() throws Exception {
    Note requestBody = getNote("new_note.json");

    mockMvc.perform(post("/note")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.owner", is(requestBody.getOwner())))
            .andExpect(jsonPath("$.title", is(requestBody.getTitle())))
            .andExpect(jsonPath("$.content", is(requestBody.getContent())))
            .andExpect(jsonPath("$.id", notNullValue()));
  }

  @Test
  public void updateNoteWithIdTest_noteExists_returns200andUpdatedNote() throws Exception {
    Note existingNote = getNote("existing_note.json");
    existingNote.setId("123456789");
    Note requestBody = getNote("existing_note.json");

    noteRepository.save(existingNote);

    mockMvc.perform(put(String.format("/note/%s", existingNote.getId()))
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.owner", is(requestBody.getOwner())))
            .andExpect(jsonPath("$.title", is(requestBody.getTitle())))
            .andExpect(jsonPath("$.content", is(requestBody.getContent())))
            .andExpect(jsonPath("$.id", is(existingNote.getId())));
  }

  @Test
  public void updateNoteWithIdTest_noteDoesNotExist_returns201andNewNote() throws Exception {
    String invalidNoteId = "123456789";
    Note requestBody = getNote("new_note.json");

    mockMvc.perform(put(String.format("/note/%s", invalidNoteId))
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.owner", is(requestBody.getOwner())))
            .andExpect(jsonPath("$.title", is(requestBody.getTitle())))
            .andExpect(jsonPath("$.content", is(requestBody.getContent())))
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.id", not(invalidNoteId)));
  }

  @Test
  public void getNoteByIdTest_noteExists_returns200andFoundNote() throws Exception {
    Note existingNote = getNote("existing_note.json");

    noteRepository.save(existingNote);

    mockMvc.perform(get(String.format("/note/%s", existingNote.getId())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.owner", is(existingNote.getOwner())))
            .andExpect(jsonPath("$.title", is(existingNote.getTitle())))
            .andExpect(jsonPath("$.content", is(existingNote.getContent())))
            .andExpect(jsonPath("$.id", is(existingNote.getId())));
  }

  @Test
  public void getNoteByIdTest_noteDoesNotExist_returns404() throws Exception {
    mockMvc.perform(get("/note/123456789"))
            .andExpect(status().isNotFound());
  }

  @Test
  public void deleteNoteWithId_noteExists_returns200andDeletesNote() throws Exception {
    Note existingNote = getNote("existing_note.json");

    noteRepository.save(existingNote);

    mockMvc.perform(delete(String.format("/note/%s", existingNote.getId())))
            .andExpect(status().isOk());

    assertFalse(noteRepository.findByNoteId(existingNote.getId()).isPresent());
  }

  @Test
  public void deleteNoteWithId_noteDoesNotExist_returns404() throws Exception {
    mockMvc.perform(delete("/note/123456789"))
            .andExpect(status().isNotFound());
  }
}
