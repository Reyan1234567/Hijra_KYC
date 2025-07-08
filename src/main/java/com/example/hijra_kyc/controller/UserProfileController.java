package com.example.hijra_kyc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hijra_kyc.dto.UserProfileInDto;
import com.example.hijra_kyc.dto.UserProfileOutDto;
import com.example.hijra_kyc.mapper.UserProfileMapper;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.service.UserProfileService;



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
    @GetMapping("/search-user/{id}")
    public ResponseEntity<UserProfileOutDto> getUserById(@PathVariable int id) {
        UserProfile user = userService.searchUserById(id);
        UserProfileOutDto dto = UserProfileMapper.toDto(user);
        return ResponseEntity.ok(dto);
    }
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
    
    
}
