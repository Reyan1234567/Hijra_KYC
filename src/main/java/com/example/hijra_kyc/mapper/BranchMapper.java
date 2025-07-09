package com.example.hijra_kyc.mapper;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.BranchInDto;
import com.example.hijra_kyc.dto.BranchOutDto;
import com.example.hijra_kyc.model.Branch;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BranchMapper {
    public Branch toEntity(BranchInDto dto){
        return Branch.builder()
        .name(dto.getName())
        .branchCode(dto.getBranchCode())
        .build();
    }
    public BranchOutDto toDto(Branch branch){
        BranchOutDto dto = new BranchOutDto();
        dto.setId(branch.getBranchId());
        dto.setName(branch.getName());
        dto.setBranchCode(branch.getBranchCode());
        return dto;
    }
}
