package com.example.hijra_kyc.mapper;

import org.mapstruct.Mapper;
import com.example.hijra_kyc.dto.UserProfileDto.UserProfileDisplayDto;
import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.UserProfileDto.UserProfileInDto;
import com.example.hijra_kyc.dto.UserProfileDto.UserProfileOutDto;
import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.model.Role;
import com.example.hijra_kyc.model.UserProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileMapper {
    public UserProfile toEntity(UserProfileInDto dto, Branch branch, Role role){
        return UserProfile.builder()

                .branch(branch)
                .roleId(role)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .userName(dto.getUserName())
                .gender(dto.getGender())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }


    public UserProfileOutDto toDto(UserProfile user){
        UserProfileOutDto dto = new UserProfileOutDto();
        dto.setId(user.getId());
        dto.setId(user.getId());
        dto.setBranchId(user.getBranch().getBranchId());
        dto.setRoleId(user.getRoleId().getRoleId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUserName(user.getUserName());
        dto.setGender(user.getGender());
        dto.setBranchAddress(user.getBranch().getName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRoleName(user.getRoleId().getRoleName()); // Role name
        dto.setStatus(user.getStatus() == 1 ? "Active" : "Blocked");
        return dto;
    }



    public UserProfileDisplayDto userDisplayDto(UserProfile user){
        return UserProfileDisplayDto.builder()
                .id(user.getId())
                .name(user.getFirstName()+" "+user.getLastName())
                .username(user.getUserName())
                .role(user.getRoleId().getRoleName())
                .branch(user.getBranch().getName())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .profilePicture(user.getPhotoUrl())
                .PresentStatus(user.getStatus())
                .build();
    }
}
