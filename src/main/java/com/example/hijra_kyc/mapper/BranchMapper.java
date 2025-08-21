package com.example.hijra_kyc.mapper;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.BranchDto.BranchInDto;
import com.example.hijra_kyc.dto.BranchDto.BranchOutDto;
import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.model.District;

@Service
public class BranchMapper {

    public Branch toEntity(BranchInDto dto, District district) {
        return Branch.builder()
                .name(dto.getName())
                .branchCode(dto.getBranchCode())
                .districtId(district)
                .status(dto.getStatus())
                .phone(dto.getPhone())
                .build();
    }

    public BranchOutDto toDto(Branch branch) {
        BranchOutDto dto = new BranchOutDto();
        dto.setId(branch.getBranchId());
        dto.setName(branch.getName());
        dto.setBranchCode(branch.getBranchCode());
        dto.setDistrictId(branch.getDistrictId().getId());
        dto.setStatus(branch.getStatus());
        dto.setPhone(branch.getPhone());
        return dto;
    }
}
