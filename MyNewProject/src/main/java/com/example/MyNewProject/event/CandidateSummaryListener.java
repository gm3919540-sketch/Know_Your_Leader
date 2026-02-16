package com.example.MyNewProject.event;

import com.example.MyNewProject.repository.Asset_DeclarationRepo;
import com.example.MyNewProject.repository.CandidateRepo;
import com.example.MyNewProject.repository.Criminal_CaseRepo;
import com.example.MyNewProject.repository.Election_ResultRepo;
import com.example.MyNewProject.tables.Candidate;
import com.example.MyNewProject.tables.Election_Result;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;

@Component
@Slf4j
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void handleElectionResultCreated(ElectionResultCreatedEvent electionResultCreatedEvent){
        log.info("inside->->->->->//******");
int id = electionResultCreatedEvent.getCandidateId();
        Candidate candidate = candidateRepo.findById(id)
                .orElseThrow(()->new IllegalArgumentException("No candidate found"));
        Election_Result electionResultlatest = electionResultRepo.
                findTopByCandidateOrderByIdDesc(candidate);
        BigDecimal totalAssets = assetDeclarationRepo
                .sumAssetsByElectionResult(electionResultlatest.getId());
        BigDecimal totalLiabilities = assetDeclarationRepo.sumLiabilitiesByElectionResult(electionResultlatest.getId());
        int  totalCases = criminalCaseRepo.countByElectionResult_Id(electionResultlatest.getId());
        log.info("Assets: {}", totalAssets);
        log.info("Liabilities: {}", totalLiabilities);
        log.info("Cases: {}", totalCases);

        candidate.setCurrentdeclared_liabilities(totalLiabilities);
        candidate.setCurrentdeclared_assets(totalAssets);
        candidate.setTotoalnumberofcase(totalCases);
        candidateRepo.save(candidate);


    }

}
