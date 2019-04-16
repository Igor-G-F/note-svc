package com.manus.noteark.notesvc.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.manus.noteark.notesvc.pojo.NoteRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public class NoteControllerIT extends AbstractIT {

  @Test
  public void createNoteTest_validRequest_noteCreatedandReturned() throws Exception {
    NoteRequest requestBody = testDataMaker.makeNoteRequest();

    MvcResult result = mockMvc.perform(post("/note")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isCreated())
            .andReturn();
            
    JsonNode responseBody = objectMapper.readTree(result.getResponse().getContentAsString());

    assertEquals(requestBody.getOwner(), responseBody.get("owner").asText());
    assertEquals(requestBody.getTitle(), responseBody.get("title").asText());
    assertEquals(requestBody.getContent(), responseBody.get("content").asText());
    assertNotNull(responseBody.get("id").asText());
  }
}