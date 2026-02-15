package com.example.MyNewProject.repository;

import com.example.MyNewProject.tables.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepo extends JpaRepository<Candidate,Integer> {
    @Override
    Optional<Candidate> findById(Integer integer);
}
