package com.example.MyNewProject.service;

import com.example.MyNewProject.repository.Asset_DeclarationRepo;
import com.example.MyNewProject.repository.CandidateRepo;
import com.example.MyNewProject.repository.Election_ResultRepo;
import com.example.MyNewProject.responseDto.CandidateElectionHistory;
import com.example.MyNewProject.tables.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    private final CandidateRepo candidateRepo;
    private  final Election_ResultRepo electionResultRepo;
    private final Asset_DeclarationRepo assetDeclarationRepo;
    private  final  AiService aiService;
    public CandidateService(
            CandidateRepo candidateRepo,
            Election_ResultRepo electionResultRepo,
            Asset_DeclarationRepo assetDeclarationRepo,
            AiService aiService
            ){
        this.candidateRepo=candidateRepo;
        this.electionResultRepo= electionResultRepo;
        this.assetDeclarationRepo=assetDeclarationRepo;
        this.aiService=aiService;
    }

    public Candidate getCandidateById(int id) {
        return candidateRepo.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Candidadate Does Not Exist"));
    }


    public List<Candidate> getCandidateByName(String name) {
        return  candidateRepo.findByName(name);
    }

    public List<Candidate> getCandidateByParty(String partyname) {
        return  candidateRepo.findByParty(partyname);
    }

    public List<Candidate> compareCandidate(int id1, int id2) {
        Candidate c1 = candidateRepo.findById(id1)
                .orElseThrow(()-> new IllegalArgumentException("candiate 1 is not found"));
        Candidate c2 =candidateRepo.findById(id2)
                .orElseThrow(()->new IllegalArgumentException("candidate 2 is not found"));
        return List.of(c1,c2);

    }

    public List<Asset_Declaration> getAssets(int idCandidate) {
        Optional<Election_Result> electionResult = electionResultRepo.findByCandidateId(idCandidate);
        List<Asset_Declaration> assetDeclarations = electionResult.get().getAssetDeclarations();
        return  assetDeclarations;
    }

    public byte[] downloadCandidateProfile(int id) {
        Candidate candidate= candidateRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Candidate does not exist"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try{
            Document document = new Document();
            PdfWriter.getInstance(document,out);
            document.open();

            document.add(new Paragraph("Candidate Profile"));
            document.add((new Paragraph(" ")));
            // Add candidate image
            if(candidate.getImageUrl() != null && !candidate.getImageUrl().isEmpty()) {
                try {
                    Image img = Image.getInstance(new URL(candidate.getImageUrl()));
                    img.scaleToFit(120, 120);   // adjust size
                    img.setAlignment(Image.ALIGN_CENTER);
                    document.add(img);
                    document.add(new Paragraph(" "));
                } catch (Exception e) {
                    System.out.println("Image not loaded: " + e.getMessage());
                }
            }

            document.add(new Paragraph("Name: "+candidate.getName()));
            document.add(new Paragraph("DOB: "+candidate.getDob()));
            document.add(new Paragraph("Party: "+candidate.getParty()));
            document.add(new Paragraph("Gender: "+candidate.getGender()));
            document.add(new Paragraph("Biography: "+candidate.getBiofraphy()));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Election Results: "));
            for(Election_Result electionResult : candidate.getElectionResult()){
                Election election = electionResult.getElection();
                document.add(new Paragraph("Year : "+ election.getYear()));
                document.add(new Paragraph("votes :"+ electionResult.getVotes_received()));
                document.add(new Paragraph("Status :"+ electionResult.getResultStatus()));


                document.add(new Paragraph("Assets:"));
                for(Asset_Declaration assetDeclaration : electionResult.getAssetDeclarations()){
                    document.add(new Paragraph("Declared Assets"+assetDeclaration.getDeclared_assets()));
                    document.add(new Paragraph("Libilities Assets"+assetDeclaration.getDeclared_liabilities()));
                }
                document.add(new Paragraph("Criminal Case:"));
                for(Criminal_Case criminalCase: electionResult.getCriminalCases()){
                    document.add(new Paragraph(criminalCase.getCase_description()
                    +"("+criminalCase.getSeverityLevel()+")"
                    ));
                }
                document.add(new Paragraph("----------------------------------"));
            }
            document.close();

        } catch (DocumentException e) {
            throw new RuntimeException("Error generating PDF");
        }

        return out.toByteArray();
    }


    public List<Candidate> getByNamePartyConstituency(String name, String party) {

        List<Candidate> candidates = candidateRepo.findByNameAndParty(name,party);
        return candidates;
    }

    public String getCandidateSummary(int id) throws Exception {
        Candidate candidate = candidateRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Candidate does not exist"));

        StringBuilder prompt = new StringBuilder();

        // Static Instructions
        prompt.append("""
                You are an impartial political data analyst.
                Provide a neutral, fact-based summary.
                Use bullet points.
                Keep it under 150 words.
                Do not give recommendations.
                \n
                """);

        //candidate basic information
        prompt.append("Candidate Information  :\n");
        prompt.append("candidate name"+ candidate.getName()+"\n");
        prompt.append("candidate age"+ candidate.getDob()+"\n");
        prompt.append("candidate gender"+ candidate.getGender()+"\n");
        prompt.append("candidate party"+ candidate.getParty()+"\n");
        prompt.append("candidate Biography"+ candidate.getBiofraphy()+"\n");
        prompt.append("candidate currentAsset"+ candidate.getCurrentdeclared_assets()+"\n");
        prompt.append("candidate currentliabilities"+ candidate.getCurrentdeclared_liabilities()+"\n");
        prompt.append("candidate totalnumberofcases"+ candidate.getTotoalnumberofcase()+"\n");

        //election result

        prompt.append("election result \n");

        if(candidate.getElectionResult()!=null){
            for(Election_Result electionResult : candidate.getElectionResult()){
                Election election = electionResult.getElection();
                //election
                prompt.append("Election Type"+election.getElectionType()+"\n");
                prompt.append("Year"+election.getYear()+"\n");

                //election result
                prompt.append("Votes Recieved"+electionResult.getVotes_received()+"\n");
                prompt.append("Result Recieved"+electionResult.getResultStatus()+"\n");
                prompt.append("Constituency "+electionResult.getConstituency()+"\n");

                //asset declartion
                prompt.append("Asset Declartion"+"\n");
                for(Asset_Declaration assetDeclaration : electionResult.getAssetDeclarations()){
                    prompt.append("Declared Assetes"+assetDeclaration.getDeclared_assets() +"\n");
                    prompt.append("Declared Liilities" +assetDeclaration.getDeclared_liabilities()+"\n");
                }
                //criminal cases
                prompt.append("criminal cases "+"\n");
                for(Criminal_Case criminalCase : electionResult.getCriminalCases()){
                    prompt.append("severity level"+criminalCase.getSeverityLevel() +"\n");
                    prompt.append("Desscription"+criminalCase.getCase_description() +"\n");

                }

            }
        }
        return aiService.generateSummary(prompt.toString());


    }

    public Page<Candidate> getAllCandidate(Pageable pageable) {
        return  candidateRepo.findAll(pageable);
    }

    public Page<Candidate> search(String name, String party, String state, String constituency,Pageable pageable) {
        System.out.println(name +" "+party+" "+state+" "+ constituency);
        return candidateRepo.search(name,party,state,constituency,pageable);

    }

    public List<CandidateElectionHistory> getElectionResultByCandidate(int id) {

        List<Election_Result> electionResults = Collections.singletonList(electionResultRepo.findByCandidateId(id)
                .orElseThrow(() -> new IllegalArgumentException("Election result correpond to this candidate does not exist")));
       List<CandidateElectionHistory> finalcandidateElectionHistories = new ArrayList<>();
        for (Election_Result electionResult: electionResults){
            CandidateElectionHistory candidateElectionHistory = new CandidateElectionHistory();
            candidateElectionHistory.setResultStatus(electionResult.getResultStatus());
            candidateElectionHistory.setVotesrecived(electionResult.getVotes_received());
            Constituency constituency = electionResult.getConstituency();
            candidateElectionHistory.setDistrict(constituency.getDistrict());
            candidateElectionHistory.setState(constituency.getState());
            candidateElectionHistory.setConstituencyname(constituency.getName());
             Election election = electionResult.getElection();
             candidateElectionHistory.setElectionType(election.getElectionType());
             candidateElectionHistory.setYear(election.getYear());
             finalcandidateElectionHistories.add(candidateElectionHistory);
        }
        return finalcandidateElectionHistories;

    }
}






