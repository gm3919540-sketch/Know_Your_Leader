package com.example.MyNewProject.repository;

import com.example.MyNewProject.tables.Candidate;
import com.example.MyNewProject.tables.Election_Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Election_ResultRepo extends JpaRepository<Election_Result,Integer> {

    @Query("SELECT DISTINCT er FROM Election_Result er WHERE er.candidate.id = :id")
    List<Election_Result> findByCandidateId( int id);

    Election_Result findTopByCandidateOrderByIdDesc(Candidate candidate);

    Optional<Election_Result> findByCandidate(Candidate candidate);
}
