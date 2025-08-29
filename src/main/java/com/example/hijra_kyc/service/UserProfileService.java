package com.example.hijra_kyc.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.hijra_kyc.dto.UserProfileDto.*;
import com.example.hijra_kyc.mapper.UserProfileMapper;
import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.model.Role;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.BranchRepository;
import com.example.hijra_kyc.repository.RoleRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;
import com.example.hijra_kyc.util.FileUpload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userRepository;
    private final BranchRepository branchRepo;
    private final RoleRepository roleRepo;
    private final UserProfileMapper mapper;
    private final FileUpload fileUpload;

    @Value("${server.port}")
    private String port;

    public UserProfileOutDto createUser(UserProfileInDto dto) {
        Branch branch = branchRepo.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        Role role = roleRepo.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        UserProfile user = mapper.toEntity(dto, branch, role);
        UserProfile savedUser = userRepository.save(user);
        return mapper.toDto(savedUser);
    }

    public List<UserProfileOutDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    // ✅ Use database ID (Long)
    public UserProfileOutDto getUserById(Long id) {
        UserProfile user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return mapper.toDto(user);
    }

    public UserProfileDisplayDto searchUserById(Long id) {
        return userRepository.findById(id)
                .map(mapper::userDisplayDto)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    // ✅ Updated to accept id: Long instead of userId: String
    public UserProfileOutDto updateUser(Long id, Long roleIdValue, Long branchIdValue,
                                        String gender, String phoneNumber, Integer status, MultipartFile photo)
            throws IOException {

        UserProfile user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        Branch branch = branchRepo.findById(branchIdValue)
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        Role role = roleRepo.findById(roleIdValue)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setBranch(branch);
        user.setRoleId(role);

        if (gender != null) user.setGender(gender);
        if (phoneNumber != null) user.setPhoneNumber(phoneNumber);
        if (status != null) user.setStatus(status);
        if (photo != null && !photo.isEmpty()) {
            user.setPhotoUrl(photo.toString()); // ⚠️ ideally store Base64 or file path
        }

        UserProfile savedUser = userRepository.save(user);
        return mapper.toDto(savedUser);
    }

    public void changeProfile(UserProfileDto dto) {
        try {
            UserProfile user = userRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getId()));

            String fileType = dto.getBase64().split(",")[0].split("/")[1].split(";")[0];

            // Build storage path
            String variable = Paths.get(user.getBranch().getName(), user.getUserName()).toString();
            String unique = Instant.now().toString().replace(":", "-").replace(".", "-") + "__." + fileType;
            String filePath = Paths.get("C:", "Users", "hp", "Videos", "Hijra_KYC", "userProfiles", variable).toString();
            String fileName = Paths.get(filePath, unique).toString();

            fileUpload.createFile(dto.getBase64(), filePath, fileName, fileType);

            user.setPhotoUrl("http://localhost:" + port + "/userProfiles/" + variable.replace("\\", "/") + "/" + unique);
            userRepository.save(user);
        } catch (IOException e) {
            log.error("File operation failed ", e);
            throw new RuntimeException("File operation failed");
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("Not the correct type of image input, ", e);
            throw new RuntimeException("Wrong image format");
        } catch (Exception e) {
            log.error("Image saving failed", e);
            throw new RuntimeException("Failed to save profile image");
        }
    }

    public UserProfile nullify(Long id) {
        UserProfile user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setPhotoUrl(null);
        userRepository.save(user);
        return user;
    }

    public List<UserProfileDisplayDto> getCheckers() {
        List<UserProfile> users = userRepository.findCheckersPresentToday();
        return users.stream().map(mapper::userDisplayDto).toList();
    }

    // ⚠️ careful: your entity doesn't have loginStatus field in the code you shared
    public void changeStatus(Long id, int status) {
        UserProfile user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        // If you meant "status" (active/inactive)
        user.setStatus(status);

        userRepository.save(user);
    }
}
