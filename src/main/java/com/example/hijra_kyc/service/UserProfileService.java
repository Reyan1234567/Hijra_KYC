package com.example.hijra_kyc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.UserProfileInDto;
import com.example.hijra_kyc.dto.UserProfileOutDto;
import com.example.hijra_kyc.mapper.UserProfileMapper;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.UserProfileRepository;

@Service
public class UserProfileService {
    private final UserProfileRepository userRepository;
    public UserProfileService(UserProfileRepository userRepository){
        this.userRepository = userRepository;
    }
    public UserProfileOutDto createUser(UserProfileInDto dto){
        UserProfile user = UserProfileMapper.toEntity(dto);
        UserProfile savedUser = userRepository.save(user);
        return UserProfileMapper.toDto(savedUser);
    }
    public List<UserProfileOutDto> getAllUsers(){
        return userRepository.findAll().stream()
        .map(UserProfileMapper::toDto)
        .collect(Collectors.toList());
    }
}
