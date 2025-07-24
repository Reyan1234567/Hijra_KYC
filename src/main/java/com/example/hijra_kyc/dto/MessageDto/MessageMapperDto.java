package com.example.hijra_kyc.dto.MessageDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageMapperDto {
    private Long id;
    private String fullName;
    private String role;
    private String branchName;
    private int status;
    private Long unreadCount;
    private String profilePhoto;
}
