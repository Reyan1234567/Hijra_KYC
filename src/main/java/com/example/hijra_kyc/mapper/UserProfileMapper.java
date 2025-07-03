package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.UserProfileInDto;
import com.example.hijra_kyc.dto.UserProfileOutDto;
import com.example.hijra_kyc.model.UserProfile;

public class UserProfileMapper {
    public static UserProfile toEntity(UserProfileInDto dto){
        return UserProfile.builder()
        .userId(dto.getUserID())
        .branchId(dto.getBranchId())
        .roleId(dto.getRoleId())
        .firstName(dto.getFirstName())
        .lastName(dto.getLastName())
        .userName(dto.getUserName())
        .gender(dto.getGender())
        .branchAddress(dto.getBranchAddress())
        .phoneNumber(dto.getPhoneNumber())
        .build();
    }
    public static UserProfileOutDto toDto(UserProfile user){
        UserProfileOutDto dto = new UserProfileOutDto();
        dto.setId(user.getId());
        dto.setUserID(user.getUserId());
        dto.setBranchId(user.getBranchId());
        dto.setRoleId(user.getRoleId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUserName(user.getUserName());
        dto.setGender(user.getGender());
        dto.setBranchAddress(user.getBranchAddress());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }
}
