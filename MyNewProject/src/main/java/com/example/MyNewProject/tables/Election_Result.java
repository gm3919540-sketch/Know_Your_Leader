package com.example.MyNewProject.tables;

import com.example.MyNewProject.enums.Result_Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class Election_Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int votes_received;
    @Enumerated(EnumType.STRING)
    private Result_Status resultStatus;
    @ManyToOne
    @JoinColumn
    private  Candidate candidate;
    @ManyToOne
    @JoinColumn
    private Election election;
    @ManyToOne
    @JoinColumn
    private Constituency constituency;
    @OneToMany(mappedBy = "electionResult",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Asset_Declaration> assetDeclarations;
    @OneToMany(mappedBy = "electionResult",cascade = CascadeType.ALL)
    private List<Criminal_Case> criminalCases ;

}
