package com.example.MyNewProject.service;

import com.example.MyNewProject.repository.CandidateRepo;
import com.example.MyNewProject.repository.NotesRepo;
import com.example.MyNewProject.tables.Candidate;
import com.example.MyNewProject.tables.Notes;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;

@Service
public class NotesService {

    private final CandidateRepo candidateRepo;
    private final  NotesRepo notesRepo;
    public NotesService(
            CandidateRepo candidateRepo,
            NotesRepo notesRepo
            ){
        this.candidateRepo=candidateRepo;
        this.notesRepo = notesRepo;
    }
    @Transactional
    public void creteNotes(int candidateId, String notes) {
        Candidate candidate = candidateRepo.findById(candidateId).orElseThrow(()-> new IllegalArgumentException("candidate not found in candidate table"));
        Notes notes1 = new Notes();
        notes1.setCandidate(candidate);
        notes1.setNote_text(notes);
        notesRepo.save(notes1);
    }
    public String getNotes(int candidateId){
        Candidate candidate = candidateRepo.findById(candidateId).orElseThrow(()-> new IllegalArgumentException("candidate does not exist"));
        Notes notes = notesRepo.findByCandidate(candidate);
        return notes.getNote_text();
    }

    public byte[] downloadPdf(int id) throws DocumentException {
        Candidate candidate = candidateRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("Candidate Does Not Exist"));
        Notes notes = candidate.getNotes();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document,out);
        document.open();
        document.add(new Paragraph("Notes Summary"+candidate.getName()));
        document.add(new Paragraph("  "));
        document.add(new Paragraph(notes.getNote_text()));
        document.close();
        return out.toByteArray();

    }
}
