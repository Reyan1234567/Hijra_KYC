package com.example.hijra_kyc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hijra_kyc.dto.DistrictDto.DistrictInDto;
import com.example.hijra_kyc.dto.DistrictDto.DistrictOutDto;
import com.example.hijra_kyc.mapper.DistrictMapper;
import com.example.hijra_kyc.model.District;
import com.example.hijra_kyc.service.DistrictService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/districts")
@Validated
@RequiredArgsConstructor
public class DistrictController {
    private final DistrictService districtService;
    private final DistrictMapper mapper;

     @PostMapping("/post-branch")
    public ResponseEntity<DistrictOutDto> postBranch(@RequestBody DistrictInDto dto) {
        DistrictOutDto result = districtService.createDistrict(dto);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/get-all-branches")
    public ResponseEntity<List<DistrictOutDto>> getAllBranches(){
        List<DistrictOutDto> result = districtService.getAllDistricts();
        return ResponseEntity.ok(result);
    
    }
    @GetMapping("/search-branch/{id}")
    public ResponseEntity<DistrictOutDto> getBranchById(@PathVariable Long id) {
        District district = districtService.searchDistrictById(id);
        DistrictOutDto dto = mapper.toDto(district);
        return ResponseEntity.ok(dto);
    }
}
