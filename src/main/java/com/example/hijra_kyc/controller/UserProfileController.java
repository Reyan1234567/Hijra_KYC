package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.UserProfileDto.*;
import com.example.hijra_kyc.mapper.UserProfileMapper;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.UserProfileRepository;
import com.example.hijra_kyc.service.UserProfileService;
import com.example.hijra_kyc.mapper.UserPrincipal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
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
    public ResponseEntity<UserProfileOutDto> postNewUser(@RequestBody @Valid UserProfileInDto dto) {
        UserProfileOutDto result = userService.createUser(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserProfileOutDto>> getAllUsers() {
        List<UserProfileOutDto> result = userService.getAllUsers();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<UserProfileOutDto> getUserById(@PathVariable Long id) {
        UserProfileOutDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/user-profile/{id}/photo")
    public ResponseEntity<String> uploadPhoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        Optional<UserProfile> optionalUser = userProfileRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserProfile user = optionalUser.get();
            try {
                user.setPhotoUrl(new String(file.getBytes())); // ⚠️ better: store path or Base64
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

    @GetMapping("/user-profile/{id}/photo")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id) {
        Optional<UserProfile> optionalUser = userProfileRepository.findById(id);
        if (optionalUser.isPresent() && optionalUser.get().getPhotoUrl() != null) {
            byte[] photo = optionalUser.get().getPhotoUrl().getBytes();
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(photo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestParam Long roleId,
            @RequestParam Long branchId,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) MultipartFile photo
    ) {
        try {
            UserProfileOutDto updatedUser = userService.updateUser(id, roleId, branchId, gender, phoneNumber, status, photo);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error updating user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user: " + e.getMessage());
        }
    }

    @PreAuthorize("#dto.id==authentication.principal.userId")
    @PatchMapping("/change-profile")
    public ResponseEntity<?> changeProfile(@Valid @RequestBody UserProfileDto dto){
        UserPrincipal userPrincipal=(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.changeProfile(dto);
        return ResponseEntity.ok("Successfully edited profile");
    }

    @PreAuthorize("#id==authentication.principal.userId")
    @PatchMapping("/delete-profile/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long id){
        userService.nullify(id);
        return ResponseEntity.ok("Successfully deleted profile");
    }

    @PreAuthorize("hasRole('HO_Manager')")
    @GetMapping("/getCheckers")
    public ResponseEntity<List<UserProfileDisplayDto>> getCheckers(){
        List<UserProfileDisplayDto> users=userService.getCheckers();
        return ResponseEntity.ok(users);
    }
}

