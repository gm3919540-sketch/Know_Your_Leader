package com.example.MyNewProject.repository;

import com.example.MyNewProject.tables.Asset_Declaration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface Asset_DeclarationRepo extends JpaRepository<Asset_Declaration,Integer> {
    @Query("Select SUM(a.declared_assets) FROM Asset_Declaration a WHERE a.electionResult.id = :id")
    BigDecimal sumAssetsByElectionResult(@Param("id") int id);

    @Query("SELECT SUM(a.declared_liabilities) FROM Asset_Declaration a WHERE a.electionResult.id = :id")
    BigDecimal sumLiabilitiesByElectionResult(@Param("id") int id);

}
