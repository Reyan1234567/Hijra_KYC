package com.example.hijra_kyc.controller;

import java.io.IOException;
import java.util.List;

import com.example.hijra_kyc.dto.UserProfileDto.UserProfileDisplayDto;
import com.example.hijra_kyc.dto.UserProfileDto.UserProfileDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get-user/{id}")
    public ResponseEntity<UserProfileDisplayDto> getUserById(@PathVariable Long id) {
        UserProfileDisplayDto user = userService.searchUserById(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/change-profile")
    public ResponseEntity<?> changeProfile(@Valid @RequestBody UserProfileDto dto){
        userService.changeProfile(dto);
        return ResponseEntity.ok("Successfully edited profile");
    }

    @PatchMapping("/delete-profile/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long id){
        UserProfile user=userService.nullify(id);
        return ResponseEntity.ok("Successfully edited profile");
    }
    
    
}
