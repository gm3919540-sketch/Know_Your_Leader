package com.example.MyNewProject.repository;

import com.example.MyNewProject.tables.Candidate;
import com.example.MyNewProject.tables.Election_Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Election_ResultRepo extends JpaRepository<Election_Result,Integer> {

    Election_Result findByCandidateId(int idCandidate);

    Election_Result findTopByCandidateOrderByIdDesc(Candidate candidate);
}
