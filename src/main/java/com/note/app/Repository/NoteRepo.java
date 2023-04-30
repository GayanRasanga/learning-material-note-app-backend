package com.note.app.Repository;

import com.note.app.Model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepo extends JpaRepository<Note, Long> {
    @Query("select n from Note n where n.title like %:title%")
    List<Note> findbykeyword(@Param("title") String word);

}