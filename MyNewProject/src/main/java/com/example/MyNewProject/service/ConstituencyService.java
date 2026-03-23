package com.example.MyNewProject.service;

import com.example.MyNewProject.repository.ConstituencyRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConstituencyService {

    private  final ConstituencyRepo constituencyRepo;
    public ConstituencyService(ConstituencyRepo constituencyRepo){
        this.constituencyRepo=constituencyRepo;
    }
    public List<String> getAllState() {
        return constituencyRepo.findAllState();
    }

    public List<String> getDistrictByState(String stateName) {
        return constituencyRepo.getDistrictByStateName(stateName);
    }

    public List<String> getConstituencyByDistrict(String districtName) {
        return constituencyRepo.getConstinyByDistrictName(districtName);
    }

    public int getID(String constituency) {
        return constituencyRepo.getId(constituency);
    }
}
