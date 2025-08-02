package com.example.hijra_kyc.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;

import com.example.hijra_kyc.dto.UserProfileDto.UserProfileDisplayDto;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.stream.Collectors;

import com.example.hijra_kyc.dto.UserProfileDto.UserProfileDto;
import com.example.hijra_kyc.util.FileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.UserProfileDto.UserProfileInDto;
import com.example.hijra_kyc.dto.UserProfileDto.UserProfileOutDto;
import com.example.hijra_kyc.mapper.UserProfileMapper;
import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.model.Role;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.BranchRepository;
import com.example.hijra_kyc.repository.RoleRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;

import lombok.RequiredArgsConstructor;

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

    public UserProfileOutDto createUser(UserProfileInDto dto){
        Branch branch = branchRepo.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        System.out.println(dto.getRoleId());
        Role role = roleRepo.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        UserProfile user = mapper.toEntity(dto,branch,role);
        UserProfile savedUser = userRepository.save(user);
        return mapper.toDto(savedUser);
    }
    public List<UserProfileOutDto> getAllUsers(){
        return userRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
    public UserProfileDisplayDto searchUserById(Long id){
        UserProfile user=userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        return mapper.userDisplayDto(user);
    }

    public void changeProfile(UserProfileDto dto){
        try{
            UserProfile user = userRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getId()));
            String fileType = dto.getBase64().split(",")[0].split("/")[1].split(";")[0];

            //check if the file sent is an image
            String variable=Paths.get(user.getBranch().getName(), user.getUserName()).toString();
            String unique=Instant.now().toString().replace(":", "-").replace(".", "-") + "__"+"."+fileType;
            String filePath = Paths.get("C:", "Users", "hp", "Videos", "Hijra_KYC", "userProfiles",variable).toString();
            String fileName = Paths.get(filePath, unique).toString();
            fileUpload.createFile(dto.getBase64(), filePath, fileName, fileType);

            user.setPhotoUrl("http://localhost:"+port+"/userProfiles/"+variable.replace("\\","/")+"/"+unique);
            userRepository.save(user);
        }
        catch(IOException e){
            log.error("File operation failed ",e);
            throw new RuntimeException("File operation failed");
        }
        catch(ArrayIndexOutOfBoundsException e){
            log.error("Not the correct type of image input, ",e);
            throw new RuntimeException("Wrong image format");
        }
        catch(Exception e){
            log.error("Image saving failed", e);
            throw new RuntimeException("Failed to save profile image");
        }
    }

    public UserProfile nullify(Long id) {
        UserProfile user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found with id: " + id));
        user.setPhotoUrl(null);
        userRepository.save(user);
        return user;
    }

    public List<UserProfileDisplayDto> getCheckers() {
        List<UserProfile> users=userRepository.findCheckersPresentToday();
        return users.stream().map(mapper::userDisplayDto).toList();
    }
}