package com.example.hijra_kyc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hijra_kyc.dto.UserProfileDto.UserProfileInDto;
import com.example.hijra_kyc.dto.UserProfileDto.UserProfileOutDto;
import com.example.hijra_kyc.mapper.UserProfileMapper;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.service.UserProfileService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/user-profiles")
@Validated
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userService;
    private final UserProfileMapper mapper;
    @PostMapping("/add-new-user")
    public ResponseEntity<UserProfileOutDto>postNewUser(@RequestBody UserProfileInDto dto) {
        UserProfileOutDto result = userService.createUser(dto);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserProfileOutDto>> getAllUsers() {
        List<UserProfileOutDto> result = userService.getAllUsers();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/search-user/{id}")
    public ResponseEntity<UserProfileOutDto> getUserById(@PathVariable Long id) {
        UserProfile user = userService.searchUserById(id);
        UserProfileOutDto dto = mapper.toDto(user);
        return ResponseEntity.ok(dto);
    }
    
    
}
