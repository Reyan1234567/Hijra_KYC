package com.example.hijra_kyc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.DistrictDto.DistrictInDto;
import com.example.hijra_kyc.dto.DistrictDto.DistrictOutDto;
import com.example.hijra_kyc.mapper.DistrictMapper;
import com.example.hijra_kyc.model.District;
import com.example.hijra_kyc.repository.DistrictRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepo;
    private final DistrictMapper mapper;

    public DistrictOutDto createDistrict(DistrictInDto dto){
        District district = mapper.toEntity(dto);
        District savedDistrict = districtRepo.save(district);
        return mapper.toDto(savedDistrict);
    }
    public List<DistrictOutDto> getAllDistricts(){
        return districtRepo.findAll().stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }
    public District searchDistrictById(Long id){
        return districtRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("District not found with id: " + id));
    }
}
