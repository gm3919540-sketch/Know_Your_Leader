package com.example.MyNewProject.responseDto;

import com.example.MyNewProject.enums.Election_Type;
import com.example.MyNewProject.enums.Result_Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateElectionHistory {
    private Result_Status resultStatus;
    private int votesrecived;
    private String district;
    private String constituencyname;
    private String state;
    private Election_Type electionType;
    private int year;
}
