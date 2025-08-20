package com.example.hijra_kyc.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.example.hijra_kyc.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.hijra_kyc.dto.UserProfileDto.UserProfileInDto;
import com.example.hijra_kyc.dto.UserProfileDto.UserProfileOutDto;
import com.example.hijra_kyc.mapper.UserProfileMapper;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.service.UserProfileService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/user-profiles")
@Validated
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userService;
    private final UserProfileMapper mapper;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @PostMapping("/add-new-user")
    public ResponseEntity<UserProfileOutDto> postNewUser(@RequestBody UserProfileInDto dto) {
        UserProfileOutDto result = userService.createUser(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserProfileOutDto>> getAllUsers() {
        List<UserProfileOutDto> result = userService.getAllUsers();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get-user/{userId}")
    public ResponseEntity<UserProfileOutDto> getUserByUserId(@PathVariable String userId) {
        UserProfileOutDto user = userService.getUserByuserId(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/user-profile/{userId}/photo")
    public ResponseEntity<String> uploadPhoto(
            @PathVariable String userId,
            @RequestParam("file") MultipartFile file) {

        Optional<UserProfile> optionalUser = userProfileRepository.findByUserId(userId);
        if (optionalUser.isPresent()) {
            UserProfile user = optionalUser.get();
            try {
                user.setPhoto(file.getBytes());
                userProfileRepository.save(user);
                return ResponseEntity.ok("Photo uploaded successfully.");
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to upload photo.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @GetMapping("/user-profile/{userId}/photo")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String userId) {
        Optional<UserProfile> optionalUser = userProfileRepository.findByUserId(userId);
        if (optionalUser.isPresent()) {
            byte[] photo = optionalUser.get().getPhoto();
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(photo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUser(
            @PathVariable String userId,
            @RequestParam Long roleId,
            @RequestParam Long branchId,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String phoneNo,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) MultipartFile photo
    ) {
        try {
            UserProfileOutDto updatedUser = userService.updateUser(userId, roleId, branchId, gender, phoneNo, status, photo);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user: " + e.getMessage());
        }
    }
}
