package com.example.MyNewProject.repository;

import com.example.MyNewProject.tables.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateRepo extends JpaRepository<Candidate,Integer> {
    @Override
    Optional<Candidate> findById(Integer integer);

    List<Candidate> findByName(String name);

    List<Candidate> findByParty(String partyname);

    List<Candidate> findByNameAndParty(String name, String party);

    int countByElectionResult(int id);
}
