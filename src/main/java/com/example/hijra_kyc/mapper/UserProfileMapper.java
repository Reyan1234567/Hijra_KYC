package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.UserProfileInDto;
import com.example.hijra_kyc.dto.UserProfileOutDto;
import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.model.UserProfile;

public class UserProfileMapper {
    public static UserProfile toEntity(UserProfileInDto dto, Branch branch){
        return UserProfile.builder()
        .userId(dto.getUserID())
        .branch(branch)
        .roleId(dto.getRoleId())
        .firstName(dto.getFirstName())
        .lastName(dto.getLastName())
        .userName(dto.getUserName())
        .gender(dto.getGender())
        .phoneNumber(dto.getPhoneNumber())
        .build();
    }
    public static UserProfileOutDto toDto(UserProfile user){
        UserProfileOutDto dto = new UserProfileOutDto();
        dto.setId(user.getId());
        dto.setUserID(user.getUserId());
        dto.setBranchId(user.getBranch().getBranchId());
        dto.setRoleId(user.getRoleId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUserName(user.getUserName());
        dto.setGender(user.getGender());
        dto.setBranchAddress(user.getBranch().getName());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }
}
