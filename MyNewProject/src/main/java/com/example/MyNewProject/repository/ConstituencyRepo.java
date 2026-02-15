package com.example.MyNewProject.repository;

import com.example.MyNewProject.tables.Constituency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConstituencyRepo extends JpaRepository<Constituency,Integer> {
    @Override
    Optional<Constituency> findById(Integer integer);
}
