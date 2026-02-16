package com.example.MyNewProject.repository;

import com.example.MyNewProject.tables.Criminal_Case;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Criminal_CaseRepo extends JpaRepository<Criminal_Case,Integer> {

    int countByElectionResult_Id(int id);
}
