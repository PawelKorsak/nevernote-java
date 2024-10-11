package org.nevernote.service.impl;

import org.nevernote.dto.NoteDTO;
import org.nevernote.entity.Note;
import org.nevernote.exception.ResourceNotFoundException;
import org.nevernote.mapper.NoteMapper;
import org.nevernote.repository.NoteRepository;
import org.nevernote.service.NoteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
    @Override
    public NoteDTO createNote(NoteDTO noteDTO) {

        Note note = NoteMapper.toNote(noteDTO);
        Note savedNote = noteRepository.save(note);
        return NoteMapper.toNoteDTO(savedNote);
    }

    @Override
    public NoteDTO getNote(Long id) {
        return NoteMapper.toNoteDTO(getNoteFromDb(id));
    }

    @Override
    public List<NoteDTO> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        return notes.stream().map(NoteMapper::toNoteDTO).collect(Collectors.toList());
    }

    @Override
    public NoteDTO updateNote(Long id, NoteDTO note) {
        Note actualNote = getNoteFromDb(id);
        actualNote.setTitle(note.getTitle());
        actualNote.setDescription(note.getDescription());
        Note updatedNote = noteRepository.save(actualNote);
        return NoteMapper.toNoteDTO(updatedNote);
    }

    @Override
    public void deleteNote(Long id) {
        Note note = getNoteFromDb(id);
        noteRepository.delete(note);
    }

    private Note getNoteFromDb(long id) {
        return noteRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Note with given id does not exist."));
    }
}
