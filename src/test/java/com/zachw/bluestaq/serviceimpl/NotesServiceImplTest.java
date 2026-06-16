package com.zachw.bluestaq.serviceimpl;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.zachw.bluestaq.dto.CreateNoteRequest;
import com.zachw.bluestaq.dto.NoteResponse;
import com.zachw.bluestaq.model.Note;
import com.zachw.bluestaq.repository.NotesRepository;

@ExtendWith(MockitoExtension.class)
class NotesServiceImplTest {

    @Mock
    private NotesRepository repository;

    @InjectMocks
    private NotesServiceImpl notesService;

    @Test
    void createNote_savesNoteAndReturnsResponse() {
        CreateNoteRequest request = new CreateNoteRequest("Test note");

        Note savedNote = new Note(
                1L,
                "Test note",
                Instant.parse("2026-06-16T00:00:00Z")
        );

        when(repository.save(any(Note.class))).thenReturn(savedNote);

        NoteResponse response = notesService.createNote(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.content()).isEqualTo("Test note");
        assertThat(response.createdAt())
                .isEqualTo(Instant.parse("2026-06-16T00:00:00Z"));

        ArgumentCaptor<Note> noteCaptor = ArgumentCaptor.forClass(Note.class);
        verify(repository).save(noteCaptor.capture());

        Note notePassedToRepository = noteCaptor.getValue();

        assertThat(notePassedToRepository.getContent()).isEqualTo("Test note");
        assertThat(notePassedToRepository.getId()).isNull();
        assertThat(notePassedToRepository.getCreatedAt()).isNotNull();
    }
}