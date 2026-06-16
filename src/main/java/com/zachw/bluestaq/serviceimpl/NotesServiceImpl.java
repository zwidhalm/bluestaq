package com.zachw.bluestaq.serviceimpl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zachw.bluestaq.dto.CreateNoteRequest;
import com.zachw.bluestaq.dto.NoteResponse;
import com.zachw.bluestaq.exception.NoteNotFoundException;
import com.zachw.bluestaq.model.Note;
import com.zachw.bluestaq.repository.NotesRepository;
import com.zachw.bluestaq.service.NotesService;

@Service
public class NotesServiceImpl implements NotesService {

    private static final Logger logger = LoggerFactory.getLogger(NotesServiceImpl.class);
    private final NotesRepository repository;

    public NotesServiceImpl(NotesRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public NoteResponse createNote(CreateNoteRequest request) {
        try {
            logger.debug("Creating note with content length: {}", request.content().length());
            Note note = new Note(null, request.content(), Instant.now());
            Note saved = repository.save(note);
            logger.info("Successfully created note with id: {}", saved.getId());
            return toResponse(saved);
        } catch (Exception e) {
            logger.error("Error creating note", e);
            throw new RuntimeException("Failed to create note", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public NoteResponse getNote(Long id) {
        try {
            logger.debug("Fetching note with id: {}", id);
            NoteResponse response = repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NoteNotFoundException("Note with id " + id + " not found"));
            logger.info("Successfully retrieved note with id: {}", id);
            return response;
        } catch (NoteNotFoundException e) {
            logger.warn("Note with id: {} not found", id);
            throw e;
        } catch (Exception e) {
            logger.error("Error fetching note with id: {}", id, e);
            throw new RuntimeException("Failed to retrieve note with id " + id, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoteResponse> getNotes() {
        try {
            logger.debug("Fetching all notes");
            List<NoteResponse> notes = repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
            logger.info("Successfully retrieved {} notes", notes.size());
            return notes;
        } catch (Exception e) {
            logger.error("Error fetching notes from database", e);
            throw new RuntimeException("Failed to retrieve notes", e);
        }
    }

    @Override
    @Transactional
    public NoteResponse deleteNote(Long id) {
        try {
            Note note = repository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note with id " + id + " not found"));
            logger.info("Deleting note with id: {}", id);
            repository.delete(note);
            logger.info("Successfully deleted note with id: {}", id);
            return toResponse(note);
        } catch (NoteNotFoundException e) {
            logger.warn("Attempted to delete non-existent note with id: {}", id);
            throw e;
        } catch (Exception e) {
            logger.error("Error deleting note with id: {}", id, e);
            throw new RuntimeException("Failed to delete note with id " + id, e);
        }
    }

    private NoteResponse toResponse(Note note) {
        return new NoteResponse(note.getId(), note.getContent(), note.getCreatedAt());
    }
}
