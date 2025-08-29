package com.example.hijra_kyc.mapper;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.BranchDto.BranchInDto;
import com.example.hijra_kyc.dto.BranchDto.BranchOutDto;
import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.model.District;

@Service
public class BranchMapper {

    // Convert DTO to Entity
    public Branch toEntity(BranchInDto dto, District district, District districtByName) {
        return Branch.builder()
                .name(dto.getName())
                .branchCode(dto.getBranchCode())
                .districtCode(district) // set district reference
                .districtName(district) // set district reference (for now both fields)
                .status(dto.getStatus())
                .phone(dto.getPhone())
                .build();
    }

    // Convert Entity to DTO
    public BranchOutDto toDto(Branch branch) {
        BranchOutDto dto = new BranchOutDto();
        dto.setId(branch.getBranchId());
        dto.setName(branch.getName());
        dto.setBranchCode(branch.getBranchCode());

        // Extract district info from entity
        if (branch.getDistrictCode() != null) {
            dto.setDistrictCode(branch.getDistrictCode().getDistrictCode());
            dto.setDistrictName(branch.getDistrictCode().getDistrictName());
        }

        dto.setStatus(branch.getStatus());
        dto.setPhone(branch.getPhone());
        return dto;
    }
}
