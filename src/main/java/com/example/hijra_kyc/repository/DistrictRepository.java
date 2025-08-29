package com.example.hijra_kyc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hijra_kyc.model.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    // Find district by its code
    Optional<District> findByDistrictCode(String districtCode);

    // Find district by its name
    Optional<District> findByDistrictName(String districtName);

}
