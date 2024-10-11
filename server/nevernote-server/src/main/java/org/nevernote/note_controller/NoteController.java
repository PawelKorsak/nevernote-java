package org.nevernote.note_controller;

import org.nevernote.dto.NoteDTO;
import org.nevernote.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
public class NoteController {
    private final NoteService noteService;
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }
    // Build add note REST API
    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@RequestBody NoteDTO note) {
        NoteDTO savedNote = noteService.createNote(note);
        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<NoteDTO> getNote(@PathVariable("id") long id) {
        NoteDTO noteDTO = noteService.getNote(id);
        return ResponseEntity.ok(noteDTO);
    }

    @GetMapping
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
     return ResponseEntity.ok(noteService.getAllNotes());
    }

    @PutMapping("{id}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable("id") long id, @RequestBody NoteDTO note) {
        NoteDTO updatedNote = noteService.updateNote(id, note);
        return ResponseEntity.ok(updatedNote);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteNote(@PathVariable("id") long id) {
        noteService.deleteNote(id);
        return ResponseEntity.ok("Note deleted");
    }
}
