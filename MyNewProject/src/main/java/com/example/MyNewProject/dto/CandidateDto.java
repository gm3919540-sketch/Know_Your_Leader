package com.example.MyNewProject.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CandidateDto {
    private String name;
    private Date dob;
    private String gender;
    private String biography;
    private String party;
    private String url;
}
