package com.zachw.bluestaq.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zachw.bluestaq.dto.CreateNoteRequest;
import com.zachw.bluestaq.dto.NoteResponse;
import com.zachw.bluestaq.service.NotesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/notes")
public class NotesController {

	private final NotesService notesService;

	public NotesController(NotesService notesService) {
		this.notesService = notesService;
	}

	@PostMapping
	public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody CreateNoteRequest request) {
		NoteResponse created = notesService.createNote(request);
		URI location = URI.create("/notes/" + created.id());
		return ResponseEntity.created(location).body(created);
	}

	@GetMapping("/{id}")
	public ResponseEntity<NoteResponse> getNote(@PathVariable("id") Long id) {
		return ResponseEntity.ok(notesService.getNote(id));
	}

	@GetMapping
	public ResponseEntity<List<NoteResponse>> getNotes() {
		return ResponseEntity.ok(notesService.getNotes());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<NoteResponse> deleteNote(@PathVariable("id") Long id) {
		NoteResponse deleted = notesService.deleteNote(id);
		return ResponseEntity.ok(deleted);
	}
}

