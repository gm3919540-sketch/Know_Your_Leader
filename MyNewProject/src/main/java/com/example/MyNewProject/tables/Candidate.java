package com.example.MyNewProject.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private String name;
    private Date dob;
    private String gender;
    private String biofraphy;
    private BigDecimal currentdeclared_assets;
    private BigDecimal currentdeclared_liabilities;
    private int totoalnumberofcase;
    @CreationTimestamp
    private Date createdAt;
    private String party;
    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    private List<Election_Result> electionResult;


}
