package com.example.MyNewProject.repository;

import com.example.MyNewProject.tables.Candidate;
import com.example.MyNewProject.tables.Constituency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//import java.lang.ScopedValue;
import java.util.List;
import java.util.Optional;

public interface ConstituencyRepo extends JpaRepository<Constituency,Integer> {
    @Override
    Optional<Constituency> findById(Integer integer);

    Optional<Constituency> findByNameAndStateAndDistrict(String state, String district, String constname);

    @Query(value = "SELECT DISTINCT state FROM constituency", nativeQuery = true)
    List<String> findAllState();

    @Query(value = "SELECT DISTINCT DISTRICT FROM CONSTITUENCY WHERE STATE =?" ,nativeQuery = true)
    List<String> getDistrictByStateName(String stateName);

    @Query(value = "SELECT DISTINCT name FROM CONSTITUENCY WHERE DISTRICT =?",nativeQuery = true)
    List<String> getConstinyByDistrictName(String districtName);

    @Query(value = "SELECT ID FROM CONTITUENCY WHERE NAME =?",nativeQuery = true)
    int getId(String constituency);

}
