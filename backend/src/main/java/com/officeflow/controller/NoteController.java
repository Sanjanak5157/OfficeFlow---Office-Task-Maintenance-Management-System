package com.officeflow.controller;

import com.officeflow.entity.Note;
import com.officeflow.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Basic Spring Boot REST Controller for Feature 1: Daily Notes (No DTO).
 */
@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Note>> getNotes(@PathVariable Long userId,
                                               @RequestParam(required = false) String search) {
        List<Note> notes = noteService.getNotesByUser(userId, search);
        return ResponseEntity.ok(notes);
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Note saved = noteService.saveNote(note);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note note) {
        note.setId(id);
        Note updated = noteService.saveNote(note);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/pin")
    public ResponseEntity<Note> togglePin(@PathVariable Long id) {
        Note toggled = noteService.togglePin(id);
        return ResponseEntity.ok(toggled);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.ok().build();
    }
}
