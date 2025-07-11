package com.example.hijra_kyc.dto.MessageDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageInDto {
    @NotNull(message="senderId is required")
    private Long sender;

    @NotBlank(message = "messageBody is required")
    private String message;

    @NotBlank(message="receiverId is required")
    private Long receiver;
}
