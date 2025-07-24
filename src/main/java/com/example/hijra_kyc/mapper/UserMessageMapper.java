package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.MessageDto.MessageMapperDto;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.util.userMessage;
import org.springframework.stereotype.Service;

@Service
public class UserMessageMapper {
    public MessageMapperDto messageMapperDto(userMessage userProfile){
        return MessageMapperDto.builder()
                .id(userProfile.getId())
                .fullName(userProfile.getFirstName()+" "+userProfile.getLastName())
                .role(userProfile.getRoleName())
                .branchName(userProfile.getBranchName())
                .unreadCount(userProfile.getCount())
                .profilePhoto(userProfile.getPhotoUrl())
                .status(userProfile.getStatus())
                .build();
    }
}
