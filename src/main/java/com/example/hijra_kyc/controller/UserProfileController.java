package com.example.hijra_kyc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hijra_kyc.service.UserProfileService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.hijra_kyc.dto.UserProfileInDto;
import com.example.hijra_kyc.dto.UserProfileOutDto;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/user-profiles")
@Validated
public class UserProfileController {
    private final UserProfileService userService;
    public UserProfileController(UserProfileService userService){
        this.userService = userService;
    }
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
    
    
}
