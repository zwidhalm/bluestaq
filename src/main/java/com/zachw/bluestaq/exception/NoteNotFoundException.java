package com.zachw.bluestaq.exception;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(String message) {
        super(message);
    }
}