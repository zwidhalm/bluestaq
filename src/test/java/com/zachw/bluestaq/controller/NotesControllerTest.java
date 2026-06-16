package com.zachw.bluestaq.controller;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zachw.bluestaq.dto.CreateNoteRequest;
import com.zachw.bluestaq.dto.NoteResponse;
import com.zachw.bluestaq.service.NotesService;

@WebMvcTest(NotesController.class)
public class NotesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private NotesService notesService;

    @Test
    void createNote_returnsCreatedNote() throws Exception {
        NoteResponse response = new NoteResponse(
                1L,
                "Test note",
                Instant.parse("2026-06-16T00:00:00Z")
        );

        when(notesService.createNote(any(CreateNoteRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/notes")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateNoteRequest("Test note")
                        )))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/notes/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value("Test note"))
                .andExpect(jsonPath("$.createdAt").value("2026-06-16T00:00:00Z"));
    }
}