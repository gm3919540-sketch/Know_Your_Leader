package com.example.MyNewProject.controller;

import com.example.MyNewProject.service.ElectionService;
import com.example.MyNewProject.tables.Election;
import com.example.MyNewProject.tables.Election_Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/elections")
public class ElectionController {

    final  private ElectionService electionService;
    public  ElectionController(ElectionService electionService){
        this.electionService= electionService;

    }

    @GetMapping("/{year}")
    public Election getElectionByYear(@PathVariable int year){
        return  null;
    }
    @GetMapping("/{year}/constituency/{constituencyId}")
    public List<Election_Result> getResults(
            @PathVariable int year,
            @PathVariable int constituencyId){
        return null;
    }
    @GetMapping("/allyears")
    public List<Integer> getAllYear(){
        return  electionService.getAllYear();
    }

}
