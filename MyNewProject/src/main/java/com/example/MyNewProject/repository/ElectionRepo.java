package com.example.MyNewProject.repository;

import com.example.MyNewProject.tables.Election;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ElectionRepo extends JpaRepository<Election,Integer> {
    @Override
    Optional<Election> findById(Integer integer);
}
