package com.example.MyNewProject.repository;

import com.example.MyNewProject.tables.Candidate;
import com.example.MyNewProject.tables.Election_Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Election_ResultRepo extends JpaRepository<Election_Result,Integer> {

    Optional<Election_Result> findByCandidateId(int idCandidate);

    Election_Result findTopByCandidateOrderByIdDesc(Candidate candidate);

    Optional<Election_Result> findByCandidate(Candidate candidate);
}
