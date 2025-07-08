package com.example.hijra_kyc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.UserProfileInDto;
import com.example.hijra_kyc.dto.UserProfileOutDto;
import com.example.hijra_kyc.mapper.UserProfileMapper;
import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.BranchRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;

@Service
public class UserProfileService {
    private final UserProfileRepository userRepository;
    private final BranchRepository branchRepo;
    public UserProfileService(UserProfileRepository userRepository, BranchRepository branchRepo){
        this.userRepository = userRepository;
        this.branchRepo = branchRepo;
    }
    public UserProfileOutDto createUser(UserProfileInDto dto){
        Branch branch = branchRepo.findById(dto.getBranchId())
        .orElseThrow(() -> new RuntimeException("Branch not found"));
        UserProfile user = UserProfileMapper.toEntity(dto,branch);
        UserProfile savedUser = userRepository.save(user);
        return UserProfileMapper.toDto(savedUser);
    }
    public List<UserProfileOutDto> getAllUsers(){
        return userRepository.findAll().stream()
        .map(UserProfileMapper::toDto)
        .collect(Collectors.toList());
    }
    public UserProfile searchUserById(int id){
         return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

    }
    public void deleteUserById(int id) {
        UserProfile user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }
}
