package org.nevernote.service;

import org.nevernote.dto.NoteDTO;

import java.util.List;

public interface NoteService {
    NoteDTO createNote(NoteDTO note);
    NoteDTO getNote(Long id);
    List<NoteDTO> getAllNotes();
    NoteDTO updateNote(Long id, NoteDTO note);
    void deleteNote(Long id);
}
