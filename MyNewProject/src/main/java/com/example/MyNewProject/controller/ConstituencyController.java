package com.example.MyNewProject.controller;

import com.example.MyNewProject.tables.Constituency;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/constituencies")
public class ConstituencyController {
    @GetMapping("/{id}")
    public Constituency getConstituency(@PathVariable int id){
        return null;
    }
    @GetMapping("/state/{stateName}")
    public List<Constituency> getByState(@PathVariable String stateName){
        return null;
    }

}
