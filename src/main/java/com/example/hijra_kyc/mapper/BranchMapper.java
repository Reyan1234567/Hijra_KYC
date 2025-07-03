package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.BranchInDto;
import com.example.hijra_kyc.dto.BranchOutDto;
import com.example.hijra_kyc.model.Branch;

public class BranchMapper {
    public static Branch toEntity(BranchInDto dto){
        return Branch.builder()
        .name(dto.getName())
        .phoneNumber(dto.getPhoneNumber())
        .build();
    }
    public static BranchOutDto toDto(Branch branch){
        BranchOutDto dto = new BranchOutDto();
        dto.setId(branch.getId());
        dto.setName(branch.getName());
        dto.setPhoneNumber(branch.getPhoneNumber());
        return dto;
    }
}
