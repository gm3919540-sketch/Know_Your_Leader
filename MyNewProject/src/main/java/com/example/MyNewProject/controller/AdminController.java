package com.example.MyNewProject.controller;

import com.example.MyNewProject.dto.CandidateDto;
import com.example.MyNewProject.dto.ConstituencyDto;
import com.example.MyNewProject.dto.ElectionDto;
import com.example.MyNewProject.dto.ElectionResultRequestDTO;
import com.example.MyNewProject.service.AdminService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    public AdminController(AdminService adminService){
        this.adminService=adminService;
    }
    @GetMapping("/test")
    public  String test(){
        return  "verified succefully";
    }
    @PostMapping("/electionresults")
    public String createElectionResult(@RequestBody ElectionResultRequestDTO dto){
        System.out.println(dto.getElectionType());
        System.out.println(dto.getResultStatus());
        System.out.println(dto.getCriminalCases());
        System.out.println(dto.getVotes_received());
        adminService.createElectionResult(dto);
        return "Election Result Created Succefully";
    }
    @PostMapping("/Candidate")
    public String createCandidate(@RequestBody CandidateDto dto){
        adminService.createCandidate(dto);
        return"candidate created succefuuly";
    }
    @PostMapping("/constituency")
    public String createConstituency(@RequestBody ConstituencyDto dto){
        adminService.createConstituency(dto);
        return"candidate created succefuuly";
    }
    @PostMapping("/election")
    public String createElection(@RequestBody ElectionDto dto){
        adminService.createElection(dto);
        return"candidate created succefuuly";
    }
}
