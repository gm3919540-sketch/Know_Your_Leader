package com.example.MyNewProject.service;

import com.example.MyNewProject.dto.CandidateDto;
import com.example.MyNewProject.dto.ConstituencyDto;
import com.example.MyNewProject.dto.ElectionDto;
import com.example.MyNewProject.dto.ElectionResultRequestDTO;
import com.example.MyNewProject.event.ElectionResultCreatedEvent;
import com.example.MyNewProject.repository.*;
import com.example.MyNewProject.tables.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AdminService {

    private final CandidateRepo candidateRepo;
    private final Asset_DeclarationRepo assetDeclarationRepo;
    private  final Criminal_CaseRepo criminalCaseRepo;
    private final ElectionRepo electionRepo;
    private final ConstituencyRepo constituencyRepo;
    private final Election_ResultRepo electionResultRepo;
    private final ApplicationEventPublisher applicationEventPublisher;
    public AdminService(
            CandidateRepo candidateRepo,
            Asset_DeclarationRepo assetDeclarationRepo,
            Criminal_CaseRepo criminalCaseRepo,
            ElectionRepo electionRepo,
            ConstituencyRepo constituencyRepo,
            Election_ResultRepo electionResultRepo,
            ApplicationEventPublisher applicationEventPublisher
    ){
        this.assetDeclarationRepo=assetDeclarationRepo;
        this.candidateRepo=candidateRepo;
        this.constituencyRepo =constituencyRepo;
        this.criminalCaseRepo=criminalCaseRepo;
        this.electionRepo =electionRepo;
        this.electionResultRepo=electionResultRepo;
        this.applicationEventPublisher= applicationEventPublisher;
    }

    @Transactional
    @CacheEvict(value = {
            "candidate",
            "candidateAssets",
            "candidateElectionHistory",
            "candidateCompare"
    }, key = "#dto.candidateId")
    public void createElectionResult(ElectionResultRequestDTO dto) {

        Candidate candidate = candidateRepo.findById(dto.getCandidateId()).orElseThrow(()->new IllegalArgumentException("candidate not found"));
        Constituency constituency = constituencyRepo.findById(dto.getConstituency_Id()).orElseThrow(()->new IllegalArgumentException("constinuecny not found"));
        Election election = electionRepo.findByElectionTypeAndYear(dto.getElectionType(),dto.getElectionYear()).orElseThrow(()->new IllegalArgumentException("election not found"));
        Election_Result electionResult = new Election_Result();

        electionResult.setCandidate(candidate);
        electionResult.setElection(election);
        electionResult.setConstituency(constituency);

        electionResult.setResultStatus(dto.getResultStatus());
        electionResult.setVotes_received(dto.getVotes_received());

        List<Asset_Declaration> assetDeclarations = dto
                .getAssets()
                .stream()
                .map(a->{
                    Asset_Declaration assetDeclaration = new Asset_Declaration();
                    assetDeclaration.setDeclared_assets(a.getDeclared_assets());
                    assetDeclaration.setDeclared_liabilities(a.getDeclared_liabilities());
                    assetDeclaration.setElectionResult(electionResult);
                    assetDeclarationRepo.save(assetDeclaration);

                    return  assetDeclaration;
                }).collect(Collectors.toList());
        electionResult.setAssetDeclarations(assetDeclarations);

        List<Criminal_Case> criminalCases = dto
                .getCriminalCases()
                .stream()
                .map(a->{
                    Criminal_Case criminalCase = new Criminal_Case();
                    criminalCase.setCase_description(a.getCase_description());
                    criminalCase.setSeverityLevel(a.getSeverityLevel());
                    criminalCase.setElectionResult(electionResult);
                    criminalCaseRepo.save(criminalCase);

                    return criminalCase;
                }).collect(Collectors.toList());

        electionResult.setCriminalCases(criminalCases);
        electionResultRepo.save(electionResult);
        applicationEventPublisher.publishEvent(new ElectionResultCreatedEvent(candidate.getId()));

    }
    @Transactional
    @CacheEvict(value = {
            "candidateByName",
            "candidateByParty"
    }, allEntries = true)
    public void createCandidate(CandidateDto dto) {
        Candidate candidate = new Candidate();
        candidate.setBiofraphy(dto.getBiography());
        candidate.setDob(dto.getDob());
        candidate.setGender(dto.getGender());
        candidate.setName(dto.getName());
        candidate.setParty(dto.getParty());
        candidate.setImageUrl(dto.getUrl());
        candidateRepo.save(candidate);

    }
    @Transactional
    @CacheEvict(value = "candidateElectionHistory", allEntries = true)
    public void createConstituency(ConstituencyDto dto) {
        Constituency constituency = new Constituency();
        constituency.setName(dto.getName());
        constituency.setDistrict(dto.getDistrict());
        constituency.setState(dto.getState());
        constituencyRepo.save(constituency);
    }
    @Transactional

    @CacheEvict(value = "candidateElectionHistory", allEntries = true)
    public void createElection(ElectionDto dto) {
        Election election = new Election();
        election.setElectionType(dto.getElectionType());
        election.setYear(dto.getYear());
        electionRepo.save(election);
    }
}
