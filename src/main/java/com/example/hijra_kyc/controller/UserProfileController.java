package com.example.hijra_kyc.controller;

import java.io.IOException;
import java.util.List;

import com.example.hijra_kyc.dto.UserProfileDto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("#dto.id=authentication.principal.userId")
    @PatchMapping("/change-profile")
    public ResponseEntity<?> changeProfile(@Valid @RequestBody UserProfileDto dto){
        userService.changeProfile(dto);
        return ResponseEntity.ok("Successfully edited profile");
    }

    @PreAuthorize("#id==authentication.principal.userId")
    @PatchMapping("/delete-profile/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long id){
        UserProfile user=userService.nullify(id);
        return ResponseEntity.ok("Successfully edited profile");
    }

    @PreAuthorize("hasRole('HO_Manager')")
    @GetMapping("/getCheckers")
    public ResponseEntity<List<UserProfileDisplayDto>> getCheckers(){
        List<UserProfileDisplayDto> users=userService.getCheckers();
        return ResponseEntity.ok(users);
    }

//    @PreAuthorize("hasRole('HO_Manager')")
//    @PatchMapping("/editPresent")
//    public ResponseEntity<?> editPresent(@Valid @RequestBody ListInterface ids){
//        List<UserProfileDisplayDto> user=userService.editPresent(ids);
//        return ResponseEntity.ok(user);
//    }
}
