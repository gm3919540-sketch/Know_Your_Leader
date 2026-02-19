package com.example.MyNewProject.controller;

import com.example.MyNewProject.repository.NotesRepo;
import com.example.MyNewProject.service.NotesService;
import com.itextpdf.text.DocumentException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
public class NotesController {
    private  final NotesService notesService;
     public NotesController(NotesService notesService){
         this.notesService= notesService;
     }

    @PostMapping("/create/{candidateId}/{notes}")
    public String createNotes(@PathVariable int candidateId,@PathVariable String notes){
        notesService.creteNotes(candidateId,notes);
        return "Succefully Created Notes";
    }
    @GetMapping("getnotes/{candidateId}")
    public  String  getNotes(@PathVariable  int candidateId){
         return notesService.getNotes(candidateId);
    }
    @GetMapping("download/pdf/{candidateId}")
    public ResponseEntity<byte[]> downloadNotes(@PathVariable int candidateId) throws DocumentException {
         byte[] pdf = notesService.downloadPdf(candidateId);
         return ResponseEntity.ok()
                 .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=notes.pdf")
                 .contentType(MediaType.APPLICATION_PDF)
                 .body(pdf);
    }

}
