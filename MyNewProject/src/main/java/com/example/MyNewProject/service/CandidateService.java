package com.example.MyNewProject.service;

import com.example.MyNewProject.repository.Asset_DeclarationRepo;
import com.example.MyNewProject.repository.CandidateRepo;
import com.example.MyNewProject.repository.Election_ResultRepo;
import com.example.MyNewProject.tables.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.List;

@Service
public class CandidateService {

    private final CandidateRepo candidateRepo;
    private  final Election_ResultRepo electionResultRepo;
    private final Asset_DeclarationRepo assetDeclarationRepo;
    public CandidateService(
            CandidateRepo candidateRepo,
            Election_ResultRepo electionResultRepo,
            Asset_DeclarationRepo assetDeclarationRepo
            ){
        this.candidateRepo=candidateRepo;
        this.electionResultRepo= electionResultRepo;
        this.assetDeclarationRepo=assetDeclarationRepo;
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
        Election_Result electionResult = electionResultRepo.findByCandidateId(idCandidate);
        List<Asset_Declaration> assetDeclarations = electionResult.getAssetDeclarations();
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
}
