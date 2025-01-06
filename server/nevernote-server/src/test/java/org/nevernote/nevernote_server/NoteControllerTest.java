package org.nevernote.nevernote_server;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.nevernote.dto.NoteDTO;
import org.nevernote.note_controller.NoteController;
import org.nevernote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;


import org.nevernote.service.impl.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(NoteController.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteDTO mockNoteDTO;

    @MockBean
    private NoteServiceImpl noteService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserServiceImpl userService;

    @Test
    void unauthenticatedGetNoteTest() throws Exception {
        // Mock the service response
        when(noteService.getNote(1L)).thenReturn(mockNoteDTO);

        // Perform GET request and verify response
        this.mockMvc.perform(get("/api/note/1"))
                .andDo(print())
                .andExpect(status().is(401));
    }

    @Test
    @WithMockUser
    void authenticatedGetNoteTest() throws Exception {
        when(mockNoteDTO.getDescription()).thenReturn("This is a sample note.");
        when(mockNoteDTO.getId()).thenReturn(1L);
        when(mockNoteDTO.getTitle()).thenReturn("Sample Note");
        when(mockNoteDTO.getOwnerId()).thenReturn(1L);
        when(noteService.getNote(1L)).thenReturn(mockNoteDTO);


        // Perform GET request and verify response
        this.mockMvc.perform(get("/api/note/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"title\":\"Sample Note\",\"description\":\"This is a sample note.\",\"ownerId\":1}"));
    }

    @Test
    void unauthenticatedCreateNote() throws Exception{
        when(noteService.createNote(mockNoteDTO)).thenReturn(mockNoteDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/note")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"title\":\"Sample Note\",\"description\":\"This is a sample note.\"}"))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser
    void authenticatedCreateNote() throws Exception{
        when(mockNoteDTO.getDescription()).thenReturn("This is a sample note.");
        when(mockNoteDTO.getId()).thenReturn(1L);
        when(mockNoteDTO.getTitle()).thenReturn("Sample Note");
        when(mockNoteDTO.getOwnerId()).thenReturn(1L);
        when(noteService.createNote(any())).thenReturn(mockNoteDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/note").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"title\":\"Sample Note\",\"description\":\"This is a sample note.\"}"))
                        .andExpect(status().is(201))
                        .andExpect(content().json("{\"id\":1,\"title\":\"Sample Note\",\"description\":\"This is a sample note.\",\"ownerId\":1}"));
    }


    @Test
    @WithMockUser
    void authenticatedDeleteNote() throws Exception{
        doNothing().when(noteService).deleteNote(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/note/1").with(csrf())).andExpect(status().isOk());
    }
}