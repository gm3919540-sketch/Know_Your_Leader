package com.example.MyNewProject.controller;

import com.example.MyNewProject.repository.ConstituencyRepo;
import com.example.MyNewProject.service.ConstituencyService;
import com.example.MyNewProject.tables.Constituency;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/constituencies")
public class ConstituencyController {

    final private ConstituencyService constituencyService;
    public ConstituencyController(ConstituencyService constituencyService){
        this.constituencyService=constituencyService;

    }

    @GetMapping("/{id}")
    public Constituency getConstituency(@PathVariable int id){
        return null;
    }
    @GetMapping("/state/{stateName}")
    public List<String> getByState(@PathVariable String stateName){

        return  constituencyService.getDistrictByState(stateName);
    }
    @GetMapping("/getallstate")
    public List<String> getallStates(){
        return constituencyService.getAllState();
    }
    @GetMapping("/by-district/{districtName}")
    public List<String> getConstituency(@PathVariable String districtName){
        return  constituencyService.getConstituencyByDistrict(districtName);
    }
    @GetMapping("/getIdByConstitency/{constituency}")
    public int getid(@PathVariable String constituency){
        return  constituencyService.getID(constituency);
    }
}
