package com.example.MyNewProject.controller;

import com.example.MyNewProject.service.CandidateService;
import com.example.MyNewProject.tables.Asset_Declaration;
import com.example.MyNewProject.tables.Candidate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate")
public class CandidateController {

    private final CandidateService candidateService;
    public CandidateController(CandidateService candidateService){
        this.candidateService=candidateService;
    }

    @GetMapping("/{id}")
    public Candidate getCandidateById(@PathVariable int id){
        return candidateService.getCandidateById(id);
    }
    @GetMapping("/search")
    public List<Candidate> searchCandidate(@RequestParam String name){
        return  null;
    }
    @GetMapping("/party")
    public List<Candidate>  getCandidatebyParty(@RequestParam String partyname){
        return  null;
    }
    @GetMapping("/compare")
    public  Object compareCandidate(@RequestParam int id1,@RequestParam int id2){
        return  null;
    }
    @GetMapping("{id}/assets")
    public List<Asset_Declaration>  getAssets(@PathVariable int id){
        return  null;
    }
    @GetMapping("/{id}/download")
    public String downloadCandidateProfile(@PathVariable int id){
        return  null;
    }



}
