package com.officeflow.service.impl;

import com.officeflow.entity.Note;
import com.officeflow.entity.User;
import com.officeflow.repository.NoteRepository;
import com.officeflow.repository.UserRepository;
import com.officeflow.service.NoteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteServiceImpl(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Note> getNotesByUser(Long userId, String search) {
        if (search != null && !search.trim().isEmpty()) {
            return noteRepository.searchUserNotes(userId, search.trim());
        }
        return noteRepository.findByUserIdOrderByIsPinnedDescUpdatedAtDesc(userId);
    }

    @Override
    public Note saveNote(Note note) {
        // If user object is null but userId is set, attach the User entity
        if (note.getUser() == null && note.getUserId() != null) {
            User author = userRepository.findById(note.getUserId()).orElse(null);
            note.setUser(author);
        } else if (note.getUser() == null) {
            // Default to first user if none provided
            note.setUser(userRepository.findById(1L).orElse(null));
        }
        return noteRepository.save(note);
    }

    @Override
    public Note togglePin(Long noteId) {
        Note note = noteRepository.findById(noteId).orElse(null);
        if (note != null) {
            note.setIsPinned(!note.getIsPinned());
            return noteRepository.save(note);
        }
        return null;
    }

    @Override
    public void deleteNote(Long noteId) {
        noteRepository.deleteById(noteId);
    }
}
