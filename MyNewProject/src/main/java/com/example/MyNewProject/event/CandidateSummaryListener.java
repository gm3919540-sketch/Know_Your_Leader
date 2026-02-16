package com.example.MyNewProject.event;

import com.example.MyNewProject.repository.Asset_DeclarationRepo;
import com.example.MyNewProject.repository.CandidateRepo;
import com.example.MyNewProject.repository.Criminal_CaseRepo;
import com.example.MyNewProject.repository.Election_ResultRepo;
import com.example.MyNewProject.tables.Candidate;
import com.example.MyNewProject.tables.Election_Result;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;

@Component

public class CandidateSummaryListener {
    private final CandidateRepo candidateRepo;
    private  final Asset_DeclarationRepo assetDeclarationRepo;
    private final Criminal_CaseRepo criminalCaseRepo;
    private final Election_ResultRepo electionResultRepo;

    public  CandidateSummaryListener(
            CandidateRepo candidateRepo,
            Asset_DeclarationRepo assetDeclarationRepo,
            Criminal_CaseRepo criminalCaseRepo,
            Election_ResultRepo electionResultRepo
    ){
        this.assetDeclarationRepo=assetDeclarationRepo;
        this.candidateRepo =candidateRepo;
        this.criminalCaseRepo =criminalCaseRepo;
        this.electionResultRepo =electionResultRepo;

    }

    @TransactionalEventListener
    public void handleElectionResultCreated(ElectionResultCreatedEvent electionResultCreatedEvent){
int id = electionResultCreatedEvent.getCandidateId();
        Candidate candidate = candidateRepo.findById(id)
                .orElseThrow(()->new IllegalArgumentException("No candidate found"));
        Election_Result electionResultlatest = electionResultRepo.
                findTopByCandidateOrderByIdDesc(candidate);
        BigDecimal totalAssets = assetDeclarationRepo
                .sumAssetsByElectionResult(electionResultlatest.getId());
        BigDecimal totalLiabilities = assetDeclarationRepo.sumLiabilitiesByElectionResult(electionResultlatest.getId());
        int  totalCases = candidateRepo.countByElectionResult(electionResultlatest.getId());
        candidate.setCurrentdeclared_liabilities(totalLiabilities);
        candidate.setCurrentdeclared_assets(totalAssets);
        candidate.setTotoalnumberofcase(totalCases);

    }

}
