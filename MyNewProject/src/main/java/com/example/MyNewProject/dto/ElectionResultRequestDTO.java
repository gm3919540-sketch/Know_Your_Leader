package com.example.MyNewProject.dto;

import com.example.MyNewProject.enums.Election_Type;
import com.example.MyNewProject.enums.Result_Status;
import lombok.Data;

import java.util.List;

@Data
public class ElectionResultRequestDTO {
    private int  candidateId;
    private Election_Type electionType;
    private  int electionYear;

    private int votes_received;
    private int constituency_Id;
    private Result_Status resultStatus;
    private List<AssetDTO> assets;
    private List<CriminalCaseDTO> criminalCases;
}
