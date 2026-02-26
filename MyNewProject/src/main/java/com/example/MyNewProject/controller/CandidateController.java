package com.example.MyNewProject.controller;

import com.example.MyNewProject.responseDto.CandidateElectionHistory;
import com.example.MyNewProject.service.CandidateService;
import com.example.MyNewProject.tables.Asset_Declaration;
import com.example.MyNewProject.tables.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
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
        return  candidateService.getCandidateByName(name);
    }
    @GetMapping("/party")
    public List<Candidate>  getCandidatebyParty(@RequestParam String partyname){
        return  candidateService.getCandidateByParty(partyname);
    }
    @GetMapping("/compare")
    public  List<Candidate> compareCandidate(@RequestParam int id1,@RequestParam int id2){
        return  candidateService.compareCandidate(id1,id2);
    }
    @GetMapping("/assets/{idCandidate}")
    public List<Asset_Declaration>  getAssets(@PathVariable int idCandidate){
        return  candidateService.getAssets(idCandidate);
    }
    @GetMapping("/profile/{id}")
    public ResponseEntity<byte[]> downloadCandidateProfile(@PathVariable int id) {
        byte[] pdf = candidateService.downloadCandidateProfile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=candidate_profile.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/nandp/{name}/{party}")
    public List<Candidate> getByNamePartyConstituency(
            @PathVariable String name,
            @PathVariable String party
            ){
        return candidateService.getByNamePartyConstituency(name,party);
    }
    @GetMapping("/ai/summary/{id}")
    public ResponseEntity<String> getcandidatesummary(@PathVariable int id) throws Exception {
        String summury =  candidateService.getCandidateSummary(id);
        return ResponseEntity.ok(summury);
    }
    @GetMapping("allCandidatedata")
    public Page<Candidate> getAllCandidate(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "9") int size
    ){
        Pageable pageable = PageRequest.of(page,size);
        return candidateService.getAllCandidate(pageable);
    }
    @GetMapping("/search/BYANUFIELD")
    public Page<Candidate> searchCandidate(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String party,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String constituency,
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "9") int size
            ){
        Pageable pageable = PageRequest.of(page,size);
          return  candidateService.search(name,party,state,constituency,pageable);
          }
   @GetMapping("/electionresult")
   public List<CandidateElectionHistory> getElectionResultByCandidate(int id){
        return candidateService.getElectionResultByCandidate(id);
   }



































}
