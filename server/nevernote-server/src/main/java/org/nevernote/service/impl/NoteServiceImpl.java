package org.nevernote.service.impl;

import jakarta.transaction.Transactional;
import org.nevernote.dto.NoteDTO;
import org.nevernote.entity.Note;
import org.nevernote.exception.ResourceNotFoundException;
import org.nevernote.mapper.NoteMapper;
import org.nevernote.repository.NoteRepository;
import org.nevernote.repository.UserRepository;
import org.nevernote.service.NoteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    public NoteServiceImpl(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public NoteDTO createNote(NoteDTO noteDTO) {

        Note note = NoteMapper.toNote(noteDTO);
        note.setOwner(userRepository.findById(noteDTO.getOwnerId()).orElseThrow(() -> new RuntimeException("User not found")));
        Note savedNote = noteRepository.save(note);
        return NoteMapper.toNoteDTO(savedNote);
    }

    @Transactional
    @Override
    public NoteDTO getNote(Long id) {
        return NoteMapper.toNoteDTO(getNoteFromDb(id));
    }

    @Override
    public List<NoteDTO> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        return notes.stream().map(NoteMapper::toNoteDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public NoteDTO updateNote(Long id, NoteDTO note) {
        Note actualNote = getNoteFromDb(id);
        actualNote.setTitle(note.getTitle());
        actualNote.setDescription(note.getDescription());
        Note updatedNote = noteRepository.save(actualNote);
        return NoteMapper.toNoteDTO(updatedNote);
    }

    @Transactional
    @Override
    public void deleteNote(Long id) {
        Note note = getNoteFromDb(id);
        noteRepository.delete(note);
    }

    private Note getNoteFromDb(long id) {
        return noteRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Note with given id does not exist."));
    }
}
