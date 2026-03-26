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
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"candidate_id", "constituency_id", "election_id"}
        )
)
public class Election_Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int votes_received;
    @Enumerated(EnumType.STRING)
    private Result_Status resultStatus;
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private  Candidate candidate;
    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;
    @ManyToOne
    @JoinColumn(name = "constituency_id")
    private Constituency constituency;
    @OneToMany(mappedBy = "electionResult",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Asset_Declaration> assetDeclarations;
    @OneToMany(mappedBy = "electionResult",cascade = CascadeType.ALL)
    private List<Criminal_Case> criminalCases ;

}
