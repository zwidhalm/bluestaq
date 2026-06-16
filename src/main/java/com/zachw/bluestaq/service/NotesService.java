package com.zachw.bluestaq.service;

import java.util.List;

import com.zachw.bluestaq.dto.CreateNoteRequest;
import com.zachw.bluestaq.dto.NoteResponse;

public interface NotesService {

	NoteResponse createNote(CreateNoteRequest request);

	NoteResponse getNote(Long id);

	List<NoteResponse> getNotes();

	NoteResponse deleteNote(Long id);

}
