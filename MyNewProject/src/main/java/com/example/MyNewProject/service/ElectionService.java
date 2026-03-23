package com.example.MyNewProject.service;

import com.example.MyNewProject.repository.ElectionRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectionService {

    private  final ElectionRepo electionRepo;
    public ElectionService(ElectionRepo electionRepo){
        this.electionRepo=electionRepo;
    }

    public List<Integer> getAllYear() {
        return  electionRepo.getAllYear();
    }
}
