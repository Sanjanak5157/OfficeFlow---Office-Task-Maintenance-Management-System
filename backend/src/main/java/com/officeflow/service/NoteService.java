package com.officeflow.service;

import com.officeflow.entity.Note;
import java.util.List;

public interface NoteService {
    List<Note> getNotesByUser(Long userId, String search);
    Note saveNote(Note note);
    Note togglePin(Long noteId);
    void deleteNote(Long noteId);
}
