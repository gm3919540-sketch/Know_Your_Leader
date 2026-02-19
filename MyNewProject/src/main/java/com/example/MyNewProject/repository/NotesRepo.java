package com.example.MyNewProject.repository;

import com.example.MyNewProject.tables.Candidate;
import com.example.MyNewProject.tables.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepo extends JpaRepository<Notes,Integer> {

    Notes findByCandidate(Candidate candidate);
}
