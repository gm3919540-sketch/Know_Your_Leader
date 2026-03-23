package com.example.MyNewProject.repository;

import com.example.MyNewProject.enums.Election_Type;
import com.example.MyNewProject.tables.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface ElectionRepo extends JpaRepository<Election,Integer> {
    @Override
    Optional<Election> findById(Integer integer);

    Optional<Election> findByElectionTypeAndYear(Election_Type electionType, int electionYear);
    @Query(value = "SELECT YEAR FROM ELECTION ",nativeQuery = true)
    List<Integer> getAllYear();
}
