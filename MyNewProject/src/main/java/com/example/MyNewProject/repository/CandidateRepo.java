package com.example.MyNewProject.repository;

import com.example.MyNewProject.tables.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CandidateRepo extends JpaRepository<Candidate,Integer> {
    @Override
    Optional<Candidate> findById(Integer integer);

    List<Candidate> findByName(String name);

    List<Candidate> findByParty(String partyname);

    List<Candidate> findByNameAndParty(String name, String party);
    Page<Candidate> findAll(Pageable pageable);

    @Query("""
SELECT c FROM Candidate c
WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
AND (:party IS NULL OR LOWER(c.party) LIKE LOWER(CONCAT('%', :party, '%')))
AND (:state IS NULL OR LOWER(c.currentState) LIKE LOWER(CONCAT('%', :state, '%')))
AND (:constituency IS NULL OR LOWER(c.currentConstituency) LIKE LOWER(CONCAT('%', :constituency, '%')))
""")
    Page<Candidate> search(
            @Param("name") String name,
            @Param("party") String party,
            @Param("state") String state,
            @Param("constituency") String constituency,
            Pageable pageable
    );
}
