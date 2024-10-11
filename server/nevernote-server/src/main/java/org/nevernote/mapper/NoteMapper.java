package org.nevernote.mapper;

import org.nevernote.dto.NoteDTO;
import org.nevernote.entity.Note;

public class NoteMapper {
    public static NoteDTO toNoteDTO(Note note) {
        NoteDTO dto = new NoteDTO();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setDescription(note.getDescription());
        return dto;
    }

    public static Note toNote(NoteDTO dto) {
        Note note = new Note();
        note.setId(dto.getId());
        note.setTitle(dto.getTitle());
        note.setDescription(dto.getDescription());
        return note;
    }
}
