package com.example.MyNewProject.tables;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Setter
@Getter
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String note_text;
    @CreationTimestamp
    private Date Created_at;
    @OneToOne
    @JoinColumn
    private Candidate candidate;
}
