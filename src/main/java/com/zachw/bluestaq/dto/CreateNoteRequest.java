package com.zachw.bluestaq.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateNoteRequest(
        @NotBlank(message = "Content must not be blank")
        String content
) {
}
