package com.zachw.bluestaq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.zachw.bluestaq.model.Note;

@Repository
public interface NotesRepository extends JpaRepository<Note, Long> {
}
