package com.example.hijra_kyc.mapper;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.DistrictDto.DistrictInDto;
import com.example.hijra_kyc.dto.DistrictDto.DistrictOutDto;
import com.example.hijra_kyc.model.District;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DistrictMapper {
    public District toEntity(DistrictInDto dto){
        return District.builder()
        .districtName(dto.getDistrictName())
        .districtCode(dto.getDistrictCode())
        .build();
    }
    public DistrictOutDto toDto(District district){
        DistrictOutDto dto = new DistrictOutDto();
        dto.setId(district.getId());
        dto.setDistrictName(district.getDistrictName());
        dto.setDistrictCode(district.getDistrictCode());
        return dto;
    }
}
