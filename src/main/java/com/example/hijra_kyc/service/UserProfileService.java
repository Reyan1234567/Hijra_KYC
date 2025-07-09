package com.example.hijra_kyc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.UserProfileDto.UserProfileInDto;
import com.example.hijra_kyc.dto.UserProfileDto.UserProfileOutDto;
import com.example.hijra_kyc.mapper.UserProfileMapper;
import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.model.Role;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.BranchRepository;
import com.example.hijra_kyc.repository.RoleRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userRepository;
    private final BranchRepository branchRepo;
    private final RoleRepository roleRepo;
    private final UserProfileMapper mapper;

    public UserProfileOutDto createUser(UserProfileInDto dto){
        Branch branch = branchRepo.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        Role role = roleRepo.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        UserProfile user = mapper.toEntity(dto,branch,role);
        UserProfile savedUser = userRepository.save(user);
        return mapper.toDto(savedUser);
    }
    public List<UserProfileOutDto> getAllUsers(){
        return userRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
    public UserProfile searchUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

    }
}